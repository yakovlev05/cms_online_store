package ru.yakovlev05.cms.catalog.service.impl;

import com.ibm.icu.text.Transliterator;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.service.TransliterationService;

@Service
public class TransliterationServiceImpl implements TransliterationService {

    private static final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

    @Override
    public String toLatin(String text) {
        Transliterator transliterator = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        return transliterator.transliterate(text);
    }
}
