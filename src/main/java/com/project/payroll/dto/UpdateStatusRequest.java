package com.project.payroll.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class UpdateStatusRequest {
    @NotNull
    private boolean active;
}
