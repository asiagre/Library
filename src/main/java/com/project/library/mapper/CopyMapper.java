package com.project.library.mapper;

import com.project.library.domain.Copy;
import com.project.library.domain.CopyDto;
import com.project.library.domain.Title;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CopyMapper {

    public Copy mapToCopy(Title title, final CopyDto copyDto) {
        return new Copy(title, copyDto.getState());
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(copy.getId(), copy.getState());
    }

    public List<CopyDto> mapToCopyDtoList(final List<Copy> copyList) {

        return copyList.stream()
                .map(copy -> mapToCopyDto(copy))
                .collect(Collectors.toList());

    }

    public List<Copy> mapToCopyList(Title title, final List<CopyDto> copyDtoList) {

        return copyDtoList.stream()
                .map(copyDto -> mapToCopy(title, copyDto))
                .collect(Collectors.toList());
    }
}
