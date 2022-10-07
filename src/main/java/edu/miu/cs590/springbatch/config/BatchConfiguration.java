package edu.miu.cs590.springbatch.config;

import edu.miu.cs590.springbatch.dao.StudentDao;
import edu.miu.cs590.springbatch.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfiguration {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private StudentDao studentDao;

    @Bean
    public FlatFileItemReader<Student> reader() {
        return new FlatFileItemReaderBuilder<Student>().name("csvDataReader")
                .resource(new ClassPathResource("students.csv"))
                .linesToSkip(1)
                .delimited()
                .names("first","last","GPA","AGE")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Student>(){
                    {
                        setTargetType(Student.class);
                    }
                }).build();
    }

    @Bean
    public StudentProcessor processor(){
        return new StudentProcessor();
    }
    @Bean
    public RepositoryItemWriter<Student> writer(){
        RepositoryItemWriter<Student> writer = new RepositoryItemWriter<>();
        writer.setRepository(studentDao);
        writer.setMethodName("save");
        return writer;
    }
    @Bean
    public Step step(){
        return stepBuilderFactory.get("set-1").<Student,Student>chunk(20)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    @Bean
    public Job runJob(){
        return jobBuilderFactory.get("importStudents")
                .flow(step())
                .end().build();
    }
}
