package com.anthem53LMS.web.lectureDto;


import com.anthem53LMS.domain.file.FileEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubmittedFileResponseDto {

    private Long id;
    private String originalName;

    public SubmittedFileResponseDto(FileEntity fileEntity){

        id = fileEntity.getId();
        originalName = fileEntity.getOriginalName();
        print();
    }

    private void print(){
        System.out.println("id :" + Long.toString(id) );
        System.out.println("originalname : " + originalName);

    }

}
