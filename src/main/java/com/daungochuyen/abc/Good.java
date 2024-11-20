package com.daungochuyen.abc;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class Good implements Rating {

    @Override
    public int getRating() {
        return 2;
    }
}
