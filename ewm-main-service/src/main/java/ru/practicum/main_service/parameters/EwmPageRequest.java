package ru.practicum.main_service.parameters;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class EwmPageRequest extends PageRequest {

    public static EwmPageRequest of(int from, int size) {
        return new EwmPageRequest(from, size);
    }

    private EwmPageRequest(int from, int size) {
        super(from / size, size, Sort.unsorted());
    }
}