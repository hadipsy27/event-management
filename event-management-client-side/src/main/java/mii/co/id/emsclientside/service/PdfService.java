package mii.co.id.emsclientside.service;

import com.lowagie.text.DocumentException;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


@Service
public class PdfService {
    
    private static final String PDF_RESOURCES = "/pdf-resources/";
    private EventService eventService;
    private SpringTemplateEngine templateEngine;

    @Autowired
    public PdfService(EventService eventService, SpringTemplateEngine templateEngine) {
        this.eventService = eventService;
        this.templateEngine = templateEngine;
    }
    
    
    public File generatePdf() throws IOException, DocumentException {
        Context context = getContext();
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
    }

    private Context getContext() {
        Context context = new Context();
        context.setVariable("events", eventService.getAll());
        return context;
    }


    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("pdf_events", context);
    }

     private File renderPdf(String html) throws IOException, DocumentException {
        File file = File.createTempFile("events", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }
    
}
