package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.ExamQuestion;
import com.cxgzs.seweb.model.ExamQuestionKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamQuestionMapper {
    int deleteByPrimaryKey(ExamQuestionKey key);

    int insert(ExamQuestion record);

    int insertSelective(ExamQuestion record);

    ExamQuestion selectByPrimaryKey(ExamQuestionKey key);

    int updateByPrimaryKeySelective(ExamQuestion record);

    int updateByPrimaryKey(ExamQuestion record);

    int selectCountByPaperId(int paper_id);

    Integer selectMaxId();

    List<Integer> selectPaperIdByLessonId(int lesson_id);

    List<Integer> selectPaperIdByLessonIdAndTypeLimited(@Param("lessonId") int lesson_id,@Param("paperType") int paper_type,
                                                 @Param("start") int start,@Param("count") int count);
    List<Integer> selectPaperIdByLessonIdAndType(@Param("lessonId") int lesson_id,@Param("paperType") int paper_type);
    String selectTitleByPaperId(int paper_id);

    int selectPaperTypeByPaperId(int paper_id);

    int deleteByPaperId(int paper_id);

    List<Integer> selectQuestionIdByPaperId(int paper_id);

    Integer selectPaperIdByTitle(String title);


}