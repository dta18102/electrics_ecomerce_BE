package com.example.medimateserver.util;

public class ConvertUtil {

    private static ConvertUtil convertUtil;

    public static ConvertUtil gI() {
        if (convertUtil == null) {
            convertUtil = new ConvertUtil();
        }
        return convertUtil;
    }

    public <T, E> E toEntity(T dto, Class<E> clazz) {
        return GsonUtil.gI().fromJson(GsonUtil.gI().toJson(dto), clazz);
    }

    public <T, E> T toDto(E entity, Class<T> clazz) {
        return GsonUtil.gI().fromJson(GsonUtil.gI().toJson(entity), clazz);
    }
}
