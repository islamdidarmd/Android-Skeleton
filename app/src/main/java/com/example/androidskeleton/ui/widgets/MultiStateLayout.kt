package com.example.androidskeleton.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.androidskeleton.R


/**
 * This class is a compound layout that shows exact view based on the passed state
 *
 * Use this class in your xml descriptor and edit attrs
 *
 * @param context The Context
 * @param attrs the AttributeSet
 * @param defStyleAttr the default Style
 */
open class MultiStateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val TAG = this.javaClass.name

    var emptyLayout: View? = null
    var loadingLayout: View? = null
    var contentLayout: View? = null

    private val array = context.obtainStyledAttributes(attrs, R.styleable.app, defStyleAttr, 0)

    override fun onFinishInflate() {
        super.onFinishInflate()

        val emptyId = array.getResourceId(R.styleable.app_emptyLayout, 0)
        val loadingId = array.getResourceId(R.styleable.app_loadingLayout, 0)
        val contentId = array.getResourceId(R.styleable.app_contentLayout, 0)

        this.emptyLayout = rootView.findViewById(emptyId)
        this.loadingLayout = rootView.findViewById(loadingId)
        this.contentLayout = rootView.findViewById(contentId)

        if (this.emptyLayout == null) {
            this.emptyLayout =
                LayoutInflater.from(context).inflate(R.layout.layout_no_data, this, false)
        }
        if (this.loadingLayout == null) {
            this.loadingLayout =
                LayoutInflater.from(context).inflate(R.layout.layout_loading, this, false)
        }

        initLayouts()
        setState(State.CONTENT)
        array.recycle()
    }

    fun initLayouts() {
        removeAllViews()
        addView(contentLayout)
        addView(emptyLayout)
        addView(loadingLayout)
    }

    /** shows exact view based on the passed state
     * @param state the current state that shows exact view
     */
    fun setState(state: State) {
        when (state) {
            State.LOADING -> {
                emptyLayout?.isVisible = false
                contentLayout?.isVisible = false
                loadingLayout?.isVisible = true
            }

            State.EMPTY -> {
                loadingLayout?.isVisible = false
                contentLayout?.isVisible = false
                emptyLayout?.isVisible = true
            }

            State.CONTENT -> {
                loadingLayout?.isVisible = false
                emptyLayout?.isVisible = false
                contentLayout?.isVisible = true
            }
        }
    }

    fun setDefaultErrorText(text: String) {
        val tvNoData = emptyLayout?.findViewById<TextView>(R.id.tvNoData)
        tvNoData?.text = text
    }

    enum class State {
        LOADING,
        EMPTY,
        CONTENT
    }
}