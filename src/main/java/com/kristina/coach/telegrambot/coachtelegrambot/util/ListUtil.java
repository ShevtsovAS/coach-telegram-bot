package com.kristina.coach.telegrambot.coachtelegrambot.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ListUtil {

    public static <T> List<List<T>> splitList(List<T> originalList, int partitionSize) {
        List<List<T>> partitions = new ArrayList<>();

        for (int i = 0; i < originalList.size(); i += partitionSize) {
            partitions.add(originalList.subList(i, Math.min(i + partitionSize, originalList.size())));
        }

        return partitions;
    }
}
