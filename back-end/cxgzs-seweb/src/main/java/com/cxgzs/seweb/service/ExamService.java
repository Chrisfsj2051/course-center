package com.cxgzs.seweb.service;

import com.alibaba.fastjson.JSONObject;
import com.cxgzs.seweb.dao.ExamMarkMapper;
import com.cxgzs.seweb.dao.ExamQuestionMapper;
import com.cxgzs.seweb.model.ExamMark;
import com.cxgzs.seweb.model.ExamMarkKey;
import com.cxgzs.seweb.model.ExamQuestion;
import com.cxgzs.seweb.model.ExamQuestionKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ExamService")
public class ExamService {
    @Resource
    ExamQuestionMapper examQuestionMapper;
    @Resource
    ExamMarkMapper examMarkMapper;

    public Map<String, Object> getPaper(int paper_id) {
        int questions_count = examQuestionMapper.selectCountByPaperId(paper_id);
        Map<String, Object> ret = new HashMap<>();
        List<Object> ques_list = new ArrayList<>();
        List<Integer> qid_list = examQuestionMapper.selectQuestionIdByPaperId(paper_id);
        if (questions_count == 0) {
            ret.put("state_code", 1);//此试卷无问题，一般不存在
            return ret;
        } else {
            for (int i = 2; i <= questions_count; i++) {//暂定题号从1开始 05.31第一题变为创建试卷时默认的空题 不返回
                Map<String, Object> listItem = new HashMap<>();
                List<Object> answers = new ArrayList<>();
                List<Object> options = new ArrayList<>();
                ExamQuestionKey k = new ExamQuestionKey();
                k.setPaperId(paper_id);
                k.setQuestionId(qid_list.get(i-1));
                ExamQuestion e = examQuestionMapper.selectByPrimaryKey(k);
                ret.put("title", e.getTitle());
                ret.put("cid", e.getLessonId());
                listItem.put("type", e.getQuestionType());
                listItem.put("question_id", e.getQuestionId());
                listItem.put("index", i-1);
                listItem.put("content", e.getContent());
                for (String answer : e.getAnswer().split("\\$\\$\\$")) {
                    answers.add(Integer.parseInt(answer));
                }
                int index = 1;
                for (int j = 1; j <= e.getChoice().split("\\$\\$\\$").length; j++) {
                    Map<String, Object> opt = new HashMap<>();
                    opt.put("option", j);
                    opt.put("choice", e.getChoice().split("\\$\\$\\$")[j - 1]);
                    options.add(opt);
//                    System.out.println(choices);
                }
                listItem.put("answer", answers);
                listItem.put("options", options);
//                System.out.println(listItem.get("answers"));
                ques_list.add(listItem);
//                System.out.println(ques_list);
//                choices.clear();
            }
            ret.put("state_code", 0);
            ret.put("paper_id", paper_id);
            ret.put("questions", ques_list);
//            System.out.println(ret.get("questions"));
            return ret;
        }
    }

    public Map<String,Object> getMyMark(int paper_id, String sno){
        ExamMarkKey tt=new ExamMarkKey(); tt.setPaperId(paper_id); tt.setSno(sno);
        ExamMark mark=examMarkMapper.selectByPrimaryKey(tt);
        Map<String,Object> ret=new HashMap<>();
        if(mark==null){ ret.put("state_code",1); ret.put("score",-1); }
        else { ret.put("state_code",0); ret.put("score",mark.getScore()); }
        return ret;
    }

    public Map<String, Object> releasePaper(JSONObject jsonObject) {
        boolean flag = true;
        String title = jsonObject.getString("title");
        int paper_id = examQuestionMapper.selectPaperIdByTitle(title)!=null?examQuestionMapper.selectPaperIdByTitle(title):getMaxIndex()+1;
        //0531 不知道是否需要修改 先放着
        int paper_type = jsonObject.getInteger("paper_type");
        int lesson_id = jsonObject.getInteger("cid");
        List<Map<String, Object>> questions = (List<Map<String, Object>>) jsonObject.get("questions");

        for (Map<String, Object> q :
                questions) {
//            System.out.println(q);
            ExamQuestion e = new ExamQuestion();
            e.setTitle(title);
            e.setPaperId(paper_id);
            e.setPaperType(paper_type);
            Integer question_type = (Integer) q.get("question_type");

            e.setQuestionType(question_type);
//            System.out.println("paper_type="+paper_type);
            e.setLessonId(lesson_id);
            List<Map<String, Object>> options = (List<Map<String, Object>>) q.get("options");
            String[] opStrA = new String[4];
            int i = 0;
            for (Map<String, Object> op : options) {
                opStrA[i++] = (String) op.get("state");
            }
            String opStr = String.join("$$$", opStrA);
//            System.out.println(opStr);
            e.setChoice(opStr);

            List<Integer> answers = (List<Integer>) q.get("answer");
            String[] ansStrA = new String[answers.size()];
            int j = 0;
            for (Integer ans : answers) {
                ansStrA[j++] = ans + "";
            }
            String ansStr = String.join("$$$", ansStrA);
            e.setAnswer(ansStr);

            String content = (String) q.get("content");
            e.setContent(content);

            if (examQuestionMapper.insertSelective(e) == 0) {
                flag = false;
            }
        }

        if (flag) {
            return getPaper(paper_id);
        } else {
            Map<String, Object> ret = new HashMap<>();
            ret.put("state_code", 1);
            return ret;
        }
    }


    public Map<String, Object> releaseSoloQues(JSONObject jsonObject) {
        int paper_id = Integer.parseInt(jsonObject.getString("paper_id"));
        String title = examQuestionMapper.selectTitleByPaperId(paper_id);
        boolean flag = true;
        int paper_type = Integer.parseInt(jsonObject.getString("paper_type"));
        int lesson_id = jsonObject.getInteger("cid");
        Map<String, Object> q = (Map<String, Object>) jsonObject.get("question");

        ExamQuestion e = new ExamQuestion();
        e.setTitle(title);
        e.setPaperId(paper_id);
        e.setPaperType(paper_type);
        String question_type = (String)q.get("question_type");
        e.setQuestionType(Integer.valueOf(question_type));

        e.setLessonId(lesson_id);
        List<Map<String, Object>> options = (List<Map<String, Object>>) q.get("options");
        String[] opStrA = new String[4];
        int i = 0;
        for (Map<String, Object> op : options) {
            opStrA[i++] = (String) op.get("state");
        }
        String opStr = String.join("$$$", opStrA);
//            System.out.println(opStr);
        e.setChoice(opStr);

        List<String> answers = (List<String>) q.get("answer");
        String[] ansStrA = new String[answers.size()];
        int j = 0;
        for (String ans : answers) {
            ansStrA[j++] = ans ;
        }
        String ansStr = String.join("$$$", ansStrA);
        e.setAnswer(ansStr);

        String content = (String) q.get("content");
        e.setContent(content);

        if (examQuestionMapper.insertSelective(e) == 0) {
            flag = false;
        }


        if (flag) {
            return getPaper(paper_id);
        } else {
            Map<String, Object> ret = new HashMap<>();
            ret.put("state_code", 1);
            return ret;
        }
    }

    public int getMaxIndex() {
        return examQuestionMapper.selectMaxId()==null?0:examQuestionMapper.selectMaxId();
    }

    public List<Integer> getPaperIdList(int lesson_id) {
        return examQuestionMapper.selectPaperIdByLessonId(lesson_id);
    }

    public List<Map<String, Object>> getAllPaperList(int lesson_id) {
        List<Integer> idlist = examQuestionMapper.selectPaperIdByLessonId(lesson_id);
        List<Map<String, Object>> ret = new ArrayList<>();
        for (Integer id : idlist) {
            Map<String, Object> item = new HashMap<>();
            item.put("paper_id", id);
            item.put("title", examQuestionMapper.selectTitleByPaperId(id));
            item.put("paper_type", examQuestionMapper.selectPaperTypeByPaperId(id));

            ret.add(item);
        }
        return ret;
    }

    public Map<String, Object> createEmptyPaper(int lesson_id,int paper_type,String title) {
        Map<String,Object> ret = new HashMap<>();

        ExamQuestion e = new ExamQuestion();
        e.setPaperId(getMaxIndex()+1);
        e.setLessonId(lesson_id);
        e.setTitle(title);
        e.setQuestionType(-1);
        e.setPaperType(paper_type);
        e.setContent("$");
        if(examQuestionMapper.insertSelective(e)!=0){
            ret.put("paper_id",examQuestionMapper.selectPaperIdByTitle(title));
            ret.put("title",title);
            ret.put("state_code",0);
        }
        else ret.put("state_code",1);
        return ret;
    }


        public Map<String, Object> getPaperList(int lesson_id,int paper_type,int start,int count) {
        List<Integer> idlist = examQuestionMapper.selectPaperIdByLessonIdAndTypeLimited(lesson_id,paper_type,start,count);
        List<Map<String, Object>> ret_list = new ArrayList<>();
        Map<String,Object> ret = new HashMap<>();
        for (Integer id : idlist) {
            Map<String, Object> item = new HashMap<>();
            item.put("paper_id", id);
            item.put("title", examQuestionMapper.selectTitleByPaperId(id));
            ret_list.add(item);
        }
        ret.put("paper_list",ret_list);
        ret.put("state_code",0);
        ret.put("totNum",examQuestionMapper.selectPaperIdByLessonIdAndType(lesson_id,paper_type).size());
        return ret;
    }

    public Map<String, Object> setPaperScore(String sno, int paper_id, int score) {
        Map<String, Object> ret = new HashMap<>();

        ExamMark em = new ExamMark();
        em.setSno(sno);
        em.setPaperId(paper_id);
        em.setScore(score);
        ExamMarkKey emk = new ExamMarkKey();
        emk.setSno(sno);
        emk.setPaperId(paper_id);
        ExamMark former = examMarkMapper.selectByPrimaryKey(emk);
        if (former == null) {//还未提交过此卷子
            examMarkMapper.insertSelective(em);
            ret.put("this_score", score);
            ret.put("highest_score", score);
        } else {//已经有过记录
            ret.put("this_score", score);
            if (former.getScore() < score) {//若比之前更高则更新
                examMarkMapper.updateByPrimaryKeySelective(em);
                ret.put("highest_score", score);
            } else {//若无则不更新
                ret.put("highest_score", former.getScore());
            }
        }
        return ret;

    }

    public Map<String, Object> delQues(int paper_id, int question_id) {
        Map<String, Object> ret = new HashMap<>();
        ExamQuestionKey eqk = new ExamQuestionKey();
        eqk.setQuestionId(question_id);
        eqk.setPaperId(paper_id);
        if (examQuestionMapper.deleteByPrimaryKey(eqk) != 0) {
            ret.put("state_code", 0);
        } else {
            ret.put("state_code", 1);
        }
        return ret;
    }

    public Map<String, Object> delPaper(int paper_id) {
        Map<String, Object> ret = new HashMap<>();

        if (examQuestionMapper.deleteByPaperId(paper_id) != 0) {
            ret.put("state_code", 0);
        } else {
            ret.put("state_code", 1);
        }
        return ret;
    }
}
