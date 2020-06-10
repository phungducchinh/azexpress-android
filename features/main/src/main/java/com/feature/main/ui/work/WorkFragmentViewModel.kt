package com.feature.main.ui.work

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.data.model.TaskModel
import com.app.data.remote.api.app.task.TaskApi
import com.app.data.remote.api.app.task.work.TaskResponse
import com.feature.main.ui.work.dto.MonthDto
import com.lib.core.fragment.BaseFragmentViewModel
import com.lib.utils.datetime.DateUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class WorkFragmentViewModel @Inject constructor() : BaseFragmentViewModel(),
    WorkFragmentContract.ViewModel {
    private var mListDate: ArrayList<MonthDto> = ArrayList()
    private var mMonth: Int = 0
    private var mYear: Int = 0
    private var mDay: Int = 0
    private var mPositionCurDay: Int = 0

    @Inject
    lateinit var mTaskApi: TaskApi
    
    init {
        val cal = Calendar.getInstance()
        cal.time = DateUtils.getCurrentDate()
        mDay = cal.get(Calendar.DAY_OF_MONTH)
        mMonth = cal.get(Calendar.MONTH)
        mYear = cal.get(Calendar.YEAR)
    }

    override
    fun getMonth(): Int {
        return mMonth
    }

    override
    fun getYear(): Int {
        return mYear
    }

    override
    fun setMonth(month: Int) {
        this.mMonth = month
    }

    override
    fun setYear(year: Int) {
        this.mYear = year
    }

    override fun getCurrentDay(): Int {
        return mPositionCurDay
    }


    private val mRequestGetListTaskLiveData: MutableLiveData<List<TaskModel>> by lazy {
        MutableLiveData<List<TaskModel>>()
    }

    private val mRequestGetListTaskLiveDataError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override
    fun requestGetListTaskLiveData(): MutableLiveData<List<TaskModel>>? {
        return mRequestGetListTaskLiveData
    }

    override
    fun requestGetListTaskErrorLiveData(): MutableLiveData<String>? {
        return mRequestGetListTaskLiveDataError
    }

    override
    fun requestGetListTask() {
        mTaskApi.getListTask()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<TaskResponse>> {
                override
                fun onSubscribe(d: Disposable) {
                }

                override
                fun onNext(response: Response<TaskResponse>) {
                    if(response.isSuccessful){
                        requestGetListTaskLiveData()?.postValue(response.body()?.data)
                    } else{
//                        val jsonAdapter = mMoshi.adapter(ErrorBody::class.java)
//                        var event: ErrorBody? = null
//                        response.errorBody()?.string()?.let {
//                            event = jsonAdapter.fromJson(it)
//                        }
//                        requestGetListTaskErrorLiveData()?.postValue(event?.message)
                    }
                }

                override
                fun onError(e: Throwable) {
                    requestGetListTaskErrorLiveData()?.postValue(e.message.toString())
                }

                override
                fun onComplete() {
                    Log.d("TAGA", "Done")
                }
            })
    }

    override
    fun requestListDate(): ArrayList<MonthDto> {
        mListDate.clear()
        // date in current month
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, mMonth)
        calendar.set(Calendar.YEAR, mYear)
        val minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in minDay..maxDay) {
            val dto = MonthDto(i, mMonth, mYear)
            mListDate.add(dto)
            if(i == mDay && mMonth ==  DateUtils.getCurrentMonth() && mYear  ==  DateUtils.getCurrentYear()){
                mPositionCurDay = mListDate.size - 1
            }
        }

        // before date if exist
        calendar.set(mYear, mMonth, minDay)
        val beforeDay =
            DateUtils.getBeforeRemainDayOfMonth(
                DateUtils.getDayFromCalendarDay(
                    calendar.get(
                        Calendar.DAY_OF_WEEK
                    )
                )
            )
        for (i in 0 until beforeDay) {
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            val day = calendar.get(Calendar.DATE)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            mListDate.add(0, MonthDto(day, month, year))
        }

        // after date if exist
        calendar.set(mYear, mMonth, maxDay)
        val afterDay =
            DateUtils.getAfterRemainDayOfMonth(DateUtils.getDayFromCalendarDay(calendar.get(Calendar.DAY_OF_WEEK)))
        for (i in 0 until afterDay) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            val day = calendar.get(Calendar.DATE)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            mListDate.add(MonthDto(day, month, year))
        }
        return mListDate
    }
}