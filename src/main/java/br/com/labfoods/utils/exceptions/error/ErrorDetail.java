package br.com.labfoods.utils.exceptions.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetail {
    private String reason;
    private String locationField;

    public ErrorDetail() {
    }

    public ErrorDetail(String reason, String locationField) {
        this.reason = reason;
        this.locationField = locationField;
    }
}