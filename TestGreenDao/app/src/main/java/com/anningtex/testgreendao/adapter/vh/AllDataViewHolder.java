package com.anningtex.testgreendao.adapter.vh;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.testgreendao.AserbaoApplication;
import com.anningtex.testgreendao.R;
import com.anningtex.testgreendao.bean.CreditCard;
import com.anningtex.testgreendao.bean.CreditCardDao;
import com.anningtex.testgreendao.bean.DaoSession;
import com.anningtex.testgreendao.bean.IdCard;
import com.anningtex.testgreendao.bean.IdCardDao;
import com.anningtex.testgreendao.bean.Student;
import com.anningtex.testgreendao.bean.StudentAndTeacherBean;
import com.anningtex.testgreendao.bean.StudentAndTeacherBeanDao;
import com.anningtex.testgreendao.bean.StudentDao;
import com.anningtex.testgreendao.bean.Teacher;
import com.anningtex.testgreendao.bean.TeacherDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: AllDataViewHolder
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 16:10
 */
public class AllDataViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.green_dao_all_data_name_tv)
    TextView mGreenDaoAllDataNameTv;
    @BindView(R.id.green_dao_all_data_id_tv)
    TextView mGreenDaoAllDataIdTv;
    @BindView(R.id.green_dao_all_data_no_tv)
    TextView mGreenDaoAllDataNoTv;
    @BindView(R.id.green_dao_all_data_age_tv)
    TextView mGreenDaoAllDataAgeTv;
    @BindView(R.id.green_dao_all_data_sex_tv)
    TextView mGreenDaoAllDataSexTv;
    @BindView(R.id.green_dao_all_data_tel_tv)
    TextView mGreenDaoAllDataTelTv;
    @BindView(R.id.green_dao_all_data_school_name_tv)
    TextView mGreenDaoAllDataSchoolNameTv;
    @BindView(R.id.green_dao_all_data_grade_tv)
    TextView mGreenDaoAllDataGradeTv;
    @BindView(R.id.green_dao_all_data_address_tv)
    TextView mGreenDaoAllDataAddressTv;
    @BindView(R.id.green_dao_all_data_id_card_tv)
    TextView mGreenDaoAllDataIdCardTv;
    @BindView(R.id.green_dao_all_data_teachers_ll)
    LinearLayout mGreenDaoAllDataTeachersLl;
    @BindView(R.id.green_dao_all_data_credit_card_ll)
    LinearLayout mGreenDaoAllDataCreditCardLl;

    public AllDataViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDataSource(Student student, AppCompatActivity activity, Context context, final IOnBackListener onBackListener) {
        if (student != null) {
            mGreenDaoAllDataNameTv.setText("名字： " + student.getName());
            mGreenDaoAllDataIdTv.setText("ID： " + student.getId());
            mGreenDaoAllDataNoTv.setText("学号：" + student.getStudentNo());
            mGreenDaoAllDataAgeTv.setText("年龄：" + student.getAge());
            mGreenDaoAllDataSexTv.setText("性别：" + student.getSex());
            mGreenDaoAllDataTelTv.setText("手机号：" + student.getTelPhone());
            mGreenDaoAllDataSchoolNameTv.setText("学校名字：" + student.getSchoolName());
            mGreenDaoAllDataGradeTv.setText("年纪： " + student.getGrade());
            mGreenDaoAllDataAddressTv.setText("家庭住址：" + student.getAddress());
            DaoSession daoSession = ((AserbaoApplication) activity.getApplication()).getDaoSession();


            QueryBuilder<IdCard> idCardQueryBuilder = daoSession.queryBuilder(IdCard.class);
            IdCard idCard = idCardQueryBuilder.where(IdCardDao.Properties.UserName.like(student.getName())).unique();
            mGreenDaoAllDataIdCardTv.setText("身份证号 ： " + idCard.getIdNo());


            mGreenDaoAllDataCreditCardLl.removeAllViews();
            QueryBuilder<CreditCard> creditCardQueryBuilder = daoSession.queryBuilder(CreditCard.class);
            List<CreditCard> creditCardList = creditCardQueryBuilder.where(CreditCardDao.Properties.StudentId.eq(String.valueOf(student.getId()) + "_string")).list();
            for (int i = 0; i < creditCardList.size(); i++) {
                CreditCard creditCard = creditCardList.get(i);
                View view = LayoutInflater.from(context).inflate(R.layout.green_dao_all_data_credit_card_item, null);
                ((TextView) view.findViewById(R.id.green_dao_credit_card_which_bank_tv)).setText(creditCard.getWhichBank() + "银行  银行卡号为：" + creditCard.getCardNum());
                mGreenDaoAllDataCreditCardLl.addView(view);
            }


            mGreenDaoAllDataTeachersLl.removeAllViews();
            QueryBuilder<StudentAndTeacherBean> studentAndTeacherBeanQueryBuilder = daoSession.queryBuilder(StudentAndTeacherBean.class);
            List<StudentAndTeacherBean> studentAndTeacherBeans = studentAndTeacherBeanQueryBuilder.where(StudentAndTeacherBeanDao.Properties.StudentId.eq(student.getId())).list();
            for (int i = 0; i < studentAndTeacherBeans.size(); i++) {
                StudentAndTeacherBean studentAndTeacherBean = studentAndTeacherBeans.get(i);
                Long teacherId = studentAndTeacherBean.getTeacherId();
                QueryBuilder<Teacher> teacherQueryBuilder = daoSession.queryBuilder(Teacher.class);
                Teacher teacher = teacherQueryBuilder.where(TeacherDao.Properties.Id.like(String.valueOf(teacherId))).unique();
                View teacherView = LayoutInflater.from(context).inflate(R.layout.green_dao_all_data_teacher_item, null);
                if (teacher == null) {
                    return;
                }
                ((TextView) teacherView.findViewById(R.id.green_dao_all_data_teacher_name_tv)).setText(" 他的" + teacher.getSubject() + " 老师 ： " + teacher.getName());
                ((TextView) teacherView.findViewById(R.id.green_dao_all_data_teacher_id_tv)).setText(" ID 为：" + teacher.getId());
                ((TextView) teacherView.findViewById(R.id.green_dao_all_data_teacher_no_tv)).setText(" 工号为： " + teacher.getTeacherNo());
                ((TextView) teacherView.findViewById(R.id.green_dao_all_data_teacher_age_tv)).setText(" 年龄：" + teacher.getAge());
                ((TextView) teacherView.findViewById(R.id.green_dao_all_data_teacher_sex_tv)).setText(" 性别：" + teacher.getSex());
                ((TextView) teacherView.findViewById(R.id.green_dao_all_data_teacher_tel_tv)).setText(" 手机号：" + teacher.getTelPhone());
                TextView flowLayout = (TextView) teacherView.findViewById(R.id.green_dao_all_data_flow_layout);

                QueryBuilder<StudentAndTeacherBean> studentAndTeacherBeanQueryBuilder1 = daoSession.queryBuilder(StudentAndTeacherBean.class);
                List<StudentAndTeacherBean> teacherBeanList = studentAndTeacherBeanQueryBuilder1.where(StudentAndTeacherBeanDao.Properties.TeacherId.eq(String.valueOf(teacher.getId()))).list();
                StringBuffer stringBuffer = null;
                for (int j = 0; j < teacherBeanList.size(); j++) {
                    stringBuffer = new StringBuffer();
                    StudentAndTeacherBean studentAndTeacherBean1 = teacherBeanList.get(j);
                    Long studentId = studentAndTeacherBean1.getStudentId();

                    QueryBuilder<Student> studentQueryBuilder = daoSession.queryBuilder(Student.class);
                    Student student1 = studentQueryBuilder.where(StudentDao.Properties.Id.eq(studentId)).unique();
                    TextView textView = new TextView(context);
                    textView.setTextColor(Color.parseColor("#0000FF"));
                    textView.setText(" 学生名字：" + student1.getName());
                    textView.setTag(student1.getId());

                    stringBuffer.append(" 学生名字：" + student1.getName());
                    stringBuffer.append(student1.getId());

//                    flowLayout.addView(textView);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackListener.onUserNameClick((Long) v.getTag());
                        }
                    });
                }

                flowLayout.setText(stringBuffer.toString());
                flowLayout.setTextColor(Color.parseColor("#0000FF"));
                mGreenDaoAllDataTeachersLl.addView(teacherView);
            }
        }
    }

    public interface IOnBackListener {
        void onUserNameClick(Long studentId);
    }
}
