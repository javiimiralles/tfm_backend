package com.backend.backend.services.pdf;

public interface PdfGeneratorService {
    byte[] generateFacturaPdf(Long idFactura);
}
