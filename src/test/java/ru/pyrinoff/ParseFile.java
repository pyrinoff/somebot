package ru.pyrinoff;

import com.github.pyrinoff.somebot.util.tgparser.ParserHistoryUtil;
import com.github.pyrinoff.somebot.util.tgparser.model.ChatExportJson;
import com.github.pyrinoff.somebot.util.tgparser.model.Message;
import com.github.pyrinoff.utils.CollectionUtil;
import com.github.pyrinoff.utils.FileUtil;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ParseFile {

    public static final String THE_HISTORY_JSON = "theHistory2.json";

    public static void main(String[] args) throws IOException {
        ChatExportJson tgHistory = ParserHistoryUtil.getTgHistory(
                FileUtil.getFilepathFromResources(
                        ParseFile.class,
                        THE_HISTORY_JSON
                ).toAbsolutePath().toString());
        System.out.println("Got file, messages: " + tgHistory.getMessages().size());

        int msgCount = 0;
        Map<String, String> idNameMap = new HashMap<>();
        Map<String, Integer> msgMap = new HashMap<>();
        Map<String, Long> lastMsgMap = new HashMap<>();
        Map<String, Long> firstMsgMap = new HashMap<>();
        Map<String, Long> joinMap = new HashMap<>();
        Map<String, Long> joinMap2 = new HashMap<>();
        //Map<String, Long> leavedMap = new HashMap<>();
        System.out.println(tgHistory.getMessages().get(0));
        for (Message oneMsg : tgHistory.getMessages()) {
            if (oneMsg.getFrom() == null) {
                //System.out.println(oneMsg.getAction());
                if ("join_group_by_link".equals(oneMsg.getAction())) {
                    //System.out.println(oneMsg);
                    if (!joinMap.containsKey(oneMsg.getActor_id()))
                        joinMap.put(oneMsg.getActor_id(), Long.parseLong(oneMsg.getDateUnixtime()));
                }
                if ("invite_members".equals(oneMsg.getAction())) {
                    String joinedName = oneMsg.getMembers().get(0);
                    if (!joinMap2.containsKey(joinedName)) {
                        joinMap2.put(joinedName, Long.parseLong(oneMsg.getDateUnixtime()));
                    }
                    else System.out.println("Пересечение по имени: " + joinedName);
                }
                continue;
            }
            msgCount++;
            String from = oneMsg.getFromId();
            if (!idNameMap.containsKey(from)) {
                idNameMap.put(oneMsg.getFromId(), oneMsg.getFrom());
                firstMsgMap.put(oneMsg.getFromId(),  Long.parseLong(oneMsg.getDateUnixtime()));
            }
            CollectionUtil.addToValue(msgMap, from, 1);
            if (oneMsg.getDateUnixtime() != null) {
                long unixTime = Long.parseLong(oneMsg.getDateUnixtime());
                if (!lastMsgMap.containsKey(from) || lastMsgMap.get(from) < unixTime) {
                    lastMsgMap.put(from, unixTime);
                }
            }
        }
        Map<String, Integer> msgMapSorted = CollectionUtil.sortByValues(msgMap, false);
        System.out.println("Всего сообщений: " + msgCount);
        System.out.println("Ник / количество сообщений / первое - последнее сообщение");
        msgMapSorted.forEach((userId, userMsgCount) -> {
            String name = idNameMap.get(userId);
            String joined = getDate(joinMap.get(userId));
            String joined2 = getDate(joinMap2.get(name));
            //String leaved = getDate(leaveMap.get(userId));
            String lastMsg = getDate(lastMsgMap.get(userId));
            String firstMsg = getDate(firstMsgMap.get(userId));
            if (name.equals("Екатерина Виноградова")) name = "Екатерина (тоже секси, но слишком много мони надо плак плак)";
            if (name.equals("Тошик")) name = "Тошик (секс символ чата)";
            if (name.equals("Александра")) name = "Александра (горячая самарская чика)";
            //if(name.equals("fridlikh")) name = "fridlikh секси энд ай ноу ит";

            String str = name //+ " " + userId
                    + ": " + userMsgCount
                    + " " + "(" + firstMsg + " - " + lastMsg + ")"
                    //+  (joined == null ? (joined2 == null ? ""
                    //    : " (добавили: " + joined2 + ")" )
                    //    : " (присоединился: " + joined + ")")
                    ;
            System.out.println(str);

        });
    }

    public static String getDate(Long unix) {
        if(unix == null) return null;
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(
                Date.from(Instant.ofEpochSecond(unix)));
    }

}
