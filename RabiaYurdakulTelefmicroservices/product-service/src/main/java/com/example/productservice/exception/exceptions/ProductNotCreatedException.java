package com.example.productservice.exception.exceptions;

import com.example.productservice.enums.IFriendlyMessageCode;
import com.example.productservice.enums.Language;
import com.example.productservice.exception.utils.FriendlyMessageUtils;
import com.example.productservice.response.FriendlyMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ProductNotCreatedException extends RuntimeException {

    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public ProductNotCreatedException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode)); //burda super ile runtime exception un constructor una ulaşmış oluyoruz
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[ProductNotCreatedException] -> message: {} devoloper message:", FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode), message);
    }
}
