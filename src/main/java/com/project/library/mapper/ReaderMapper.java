package com.project.library.mapper;

import com.project.library.domain.Reader;
import com.project.library.domain.ReaderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderMapper {

    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(readerDto.getId(), readerDto.getFirstName(), readerDto.getLastName(), readerDto.getDateCreated(), readerDto.getMail(), readerDto.isActive());
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(reader.getId(), reader.getFirstName(), reader.getLastName(), reader.getMail(), reader.getDateCreated(), reader.isActive());
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readerList) {
        return readerList.stream()
                .map(reader -> mapToReaderDto(reader))
                .collect(Collectors.toList());
    }
}
