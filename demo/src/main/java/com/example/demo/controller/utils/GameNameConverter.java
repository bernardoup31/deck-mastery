package com.example.demo.controller.utils;

import com.example.demo.games.GameName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class GameNameConverter implements Converter<String, GameName> {

    @Override
    public GameName convert(String source) {
        return GameName.valueOf(source.toUpperCase());
    }
}
