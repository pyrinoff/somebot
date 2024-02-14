package com.github.pyrinoff.somebot.service;


import com.github.pyrinoff.somebot.exception.model.IdNullException;
import com.github.pyrinoff.somebot.exception.service.FloodPaymentException;
import com.github.pyrinoff.somebot.exception.service.QiwiApiChangedException;
import com.github.pyrinoff.somebot.model.Payment;
import com.github.pyrinoff.somebot.repository.PaymentRepository;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;

@Service
public class PaymentQiwiService {

    public static final Logger logger = LoggerFactory.getLogger(PaymentQiwiService.class);

    @Autowired
    public PropertyService propertyService;

    @Autowired
    public PaymentRepository repository;

    public Payment get(Long paymentId) throws IdNullException {
        if (paymentId == null) throw new IdNullException();
        return repository.findById(paymentId).orElse(null);
    }

    @SneakyThrows
    public Payment save(@Nullable Payment payment) {
        if (payment == null) throw new NullPointerException();
        repository.save(payment);
        return payment;
    }

    public Payment createPayment(Payment payment) throws Exception {
        BillPaymentClient client = BillPaymentClientFactory.createDefault(propertyService.getQiwiSecretKey());
        String uuid = UUID.randomUUID().toString();
        Double amount = payment.getAmount();
        checkLimitsQiwi(amount, payment.getCurrency());

        Currency currency = getCurrencyByCodeQiwi(payment.getCurrency());
        MoneyAmount moneyAmount = new MoneyAmount(BigDecimal.valueOf(amount), currency);
        String comment = payment.getComment();
        ZonedDateTime ttl = ZonedDateTime.now().plusHours(12);

        CreateBillInfo createBillInfo = new CreateBillInfo(uuid, moneyAmount, comment, ttl, null, null);
        BillResponse billResponse = client.createBill(createBillInfo);
        logger.debug("billResponse: " + billResponse.toString());

        String billStatus = billResponse.getStatus().getValue().getValue();
        Integer universal = getUniversalBillStatus(billStatus);

        if (universal == null) {
            throw new Exception("createPaymentQiwi: universal status not found for internal status: " + billResponse.getStatus());
        }

        if (universal != Payment.PAYMENT_STATUS_AWAITING) {
            throw new Exception("createPaymentQiwi: status of created response is not WAITING, it is: " + billResponse.getStatus());
        }

        payment.setExternalId(billResponse.getBillId());
        payment.setUrl(billResponse.getPayUrl());
        payment.setStatus(Payment.PAYMENT_STATUS_AWAITING);
        save(payment);
        return payment;
    }

    private static Currency getCurrencyByCodeQiwi(Integer currencyCode) throws Exception {
        if (currencyCode.equals(Payment.PAYMENT_CURRENCY_RUB)) {
            return Currency.getInstance("RUB");
        }
        if (currencyCode.equals(Payment.PAYMENT_CURRENCY_USD)) {
            return Currency.getInstance("USD");
        }
        throw new Exception("Cant get payment currency by code!");
    }

    private static void checkLimitsQiwi(Double amount, Integer currencyCode) throws Exception {
        if (amount < 0.01)
            throw new Exception("checkLimitsQiwi failed: amount < 0.01");

        if (currencyCode.equals(Payment.PAYMENT_CURRENCY_RUB)) {
            if (amount > 60000)
                throw new Exception("checkLimitsQiwi failed: amount > 60000");
        }
        if (currencyCode.equals(Payment.PAYMENT_CURRENCY_USD)) {
            if (amount > 800)
                throw new Exception("checkLimitsQiwi failed: amount > 800 USD");
        }
    }

    public void checkPayment(Payment payment, boolean checkForFlood)
            throws FloodPaymentException, QiwiApiChangedException {
        logger.info("checkPayment (PaymentQiwi)");
        Integer billStatusUniversal;

        if (checkForFlood &&
                ((System.currentTimeMillis() - payment.getLastChecked().getTime()) / 1000 < propertyService.getQiwiFloodIntervalSeconds())
        ) {
            throw new FloodPaymentException();
        }
        payment.setLastChecked(new Timestamp(System.currentTimeMillis()));
        repository.save(payment);

        if (Boolean.FALSE.equals(propertyService.getQiwiMockEnabled())) { //NO MOCK
            BillPaymentClient client = BillPaymentClientFactory.createDefault(propertyService.getQiwiSecretKey());
            String uuid = payment.getExternalId();
            if (uuid == null) {
                throw new QiwiApiChangedException("checkPayment (PaymentQiwi): Has not unique UUID (external_id) in payment!");
            }
            BillResponse billResponse = client.getBillInfo(uuid);
            String billStatus = billResponse.getStatus().getValue().getValue();
            billStatusUniversal = getUniversalBillStatus(billStatus);
            if (billStatusUniversal == null) {
                throw new QiwiApiChangedException("checkPayment (PaymentQiwi): universal status not found for internal status: " + billStatus);
            }
            Integer previousStatus = payment.getStatus();
            if (previousStatus.equals(billStatusUniversal)) {
                logger.info("checkPayment (PaymentQiwi): status not changed");
                return;
            }

        } else { //MOCK
            logger.warn("checkPayment mocked!");
            billStatusUniversal = Payment.PAYMENT_STATUS_PAID;
        }
        payment.setStatus(billStatusUniversal);
        repository.save(payment);
    }

    public static Integer getUniversalBillStatus(String internalStatus) {
        switch (internalStatus) {
            case "WAITING":
                return Payment.PAYMENT_STATUS_AWAITING;
            case "PAID":
                return Payment.PAYMENT_STATUS_PAID;
            case "REJECTED":
                return Payment.PAYMENT_STATUS_REJECTED;
            case "EXPIRED":
                return Payment.PAYMENT_STATUS_EXPIRED;
            default:
                return null;
        }
    }

}
