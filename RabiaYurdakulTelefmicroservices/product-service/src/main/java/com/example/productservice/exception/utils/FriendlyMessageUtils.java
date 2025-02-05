package com.example.productservice.exception.utils;

import com.example.productservice.enums.IFriendlyMessageCode;
import com.example.productservice.enums.Language;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
@UtilityClass
public class FriendlyMessageUtils {

    private static final String RESOURCE_BUNDLE_NAME="FriendlyMessage";
    private static final String SPECIAL_CHARACTER="__";


    public static String getFriendlyMessage(Language language, IFriendlyMessageCode messageCode ) {
        String messageKey=null;
        try{
            Locale locale=new Locale(language.name());
            ResourceBundle resourceBundle=ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME,locale);
            messageKey=messageCode.getClass().getSimpleName()+SPECIAL_CHARACTER+messageCode;
            return resourceBundle.getString(messageKey);
        }catch (MissingResourceException missingResourceException) {
            log.error("Friendly message not found for key: {}", messageKey); //burda süslü parantez kullanmasının sebebi onun yerine bu değişkeni atamak eğer resource bundle ile ilgili kod bulunamazsa bu hatayı yazacak
            return null;
        }
    }

}
