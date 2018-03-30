package com.report.repository;

import com.report.domain.ReportTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportTemplateRepositoryTest {

    @Autowired
    private ReportTemplateRepository reportTemplateRepository;

    @Test
    public void findByReportTemplate() {
    }

    @Test
    public void listAll() {
        Page<ReportTemplate> reportTemplates = reportTemplateRepository.findByTemplateNameLikeOrderByUpdateTimeDesc("%ggg%", Pageable.unpaged());
        System.out.println(reportTemplates.getContent());
    }
}