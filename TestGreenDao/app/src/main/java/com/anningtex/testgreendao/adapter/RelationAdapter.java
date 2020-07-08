package com.anningtex.testgreendao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.testgreendao.AserbaoApplication;
import com.anningtex.testgreendao.R;
import com.anningtex.testgreendao.adapter.vh.AllDataViewHolder;
import com.anningtex.testgreendao.adapter.vh.CreditCardViewHolder;
import com.anningtex.testgreendao.adapter.vh.IdCardViewHolder;
import com.anningtex.testgreendao.adapter.vh.StudentViewHolder;
import com.anningtex.testgreendao.adapter.vh.TeacherViewHolder;
import com.anningtex.testgreendao.bean.CreditCard;
import com.anningtex.testgreendao.bean.DaoSession;
import com.anningtex.testgreendao.bean.IdCard;
import com.anningtex.testgreendao.bean.IdCardDao;
import com.anningtex.testgreendao.bean.Student;
import com.anningtex.testgreendao.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RelationAdapter
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 15:59
 */
public class RelationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int STUDENT = 0;
    public static final int TEACHER = 1;
    public static final int IDCARD = 2;
    public static final int CREDITCARD = 3;
    public static final int ALLDATA = 4;


    private int cuurType = STUDENT;

    private Context mContext;
    private AppCompatActivity activity;
    private DaoSession daoSession;
    private RecyclerView mRecyclerView;

    List<Student> mStudentList = new ArrayList<>();
    List<Teacher> mTeacherList = new ArrayList<>();
    List<IdCard> mIdCardList = new ArrayList<>();
    List<CreditCard> mCreditCardList = new ArrayList<>();

    public RelationAdapter(Context mContext, AppCompatActivity activity, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.activity = activity;
        mRecyclerView = recyclerView;
        daoSession = ((AserbaoApplication) activity.getApplication()).getDaoSession();
    }

    public void refreshAllData(int type) {
        cuurType = type;

        switch (type) {
            case ALLDATA:
            case STUDENT:
                mStudentList = daoSession.loadAll(Student.class);
                break;
            case TEACHER:
                mTeacherList = daoSession.loadAll(Teacher.class);
                break;
            case IDCARD:
                mIdCardList = daoSession.loadAll(IdCard.class);
                break;
            case CREDITCARD:
                mCreditCardList = daoSession.loadAll(CreditCard.class);
                break;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return cuurType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        switch (cuurType) {
            case STUDENT:
                view = inflater.inflate(R.layout.green_dao_student_item, viewGroup, false);
                return new StudentViewHolder(view);
            case IDCARD:
                view = inflater.inflate(R.layout.green_dao_id_card_item, viewGroup, false);
                return new IdCardViewHolder(view);
            case CREDITCARD:
                view = inflater.inflate(R.layout.green_dao_credit_card_item, viewGroup, false);
                return new CreditCardViewHolder(view);
            case TEACHER:
                view = inflater.inflate(R.layout.green_dao_teacher_item, viewGroup, false);
                return new TeacherViewHolder(view);
            case ALLDATA:
                view = inflater.inflate(R.layout.green_dao_all_data_item, viewGroup, false);
                return new AllDataViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof StudentViewHolder) {
            if (i < mStudentList.size()) {
                Student student = mStudentList.get(i);
                ((StudentViewHolder) viewHolder).setDataSource(student);
                ((StudentViewHolder) viewHolder).mGreenDaoStudentNameTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = ((StudentViewHolder) viewHolder).mGreenDaoStudentNameTv.getText().toString().trim();
                        String[] split = name.split(" ");
                        String userName = split[1];
                        mIdCardList = daoSession.queryBuilder(IdCard.class).where(IdCardDao.Properties.UserName.like(userName)).list();
                        cuurType = IDCARD;
                        notifyDataSetChanged();
                    }
                });
            }
        } else if (viewHolder instanceof IdCardViewHolder) {
            if (i < mIdCardList.size()) {
                IdCard idCard = mIdCardList.get(i);
                ((IdCardViewHolder) viewHolder).setDataSource(idCard);
            }
        } else if (viewHolder instanceof CreditCardViewHolder) {
            if (i < mCreditCardList.size()) {
                CreditCard creditCard = mCreditCardList.get(i);
                ((CreditCardViewHolder) viewHolder).setDataSource(creditCard);
            }
        } else if (viewHolder instanceof TeacherViewHolder) {
            if (i < mTeacherList.size()) {
                Teacher teacher = mTeacherList.get(i);
                ((TeacherViewHolder) viewHolder).setDataSource(teacher);
            }
        } else if (viewHolder instanceof AllDataViewHolder) {
            if (i < mStudentList.size()) {
                Student student = mStudentList.get(i);
                ((AllDataViewHolder) viewHolder).setDataSource(student, activity, mContext, new AllDataViewHolder.IOnBackListener() {
                    @Override
                    public void onUserNameClick(Long studentId) {
                        for (int i1 = 0; i1 < mStudentList.size(); i1++) {
                            Student student1 = mStudentList.get(i1);
                            if (student1.getId() == studentId) {
                                mRecyclerView.smoothScrollToPosition(i1);
                                break;
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        switch (cuurType) {
            case ALLDATA:
            case STUDENT:
                if (mStudentList != null) {
                    ret = ret + mStudentList.size();
                }
                break;
            case TEACHER:
                if (mTeacherList != null) {
                    ret = ret + mTeacherList.size();
                }
                break;
            case IDCARD:
                if (mIdCardList != null) {
                    ret = ret + mIdCardList.size();
                }
                break;
            case CREDITCARD:
                if (mCreditCardList != null) {
                    ret = ret + mCreditCardList.size();
                }
                break;
        }
        return ret;
    }


}
