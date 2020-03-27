package lpzmrc.test.djungle.io.ui.controller

import com.airbnb.epoxy.TypedEpoxyController
import lpzmrc.test.djungle.io.ToDoEmptyBindingModel_
import lpzmrc.test.djungle.io.ToDoInvalidatedBindingModel_
import lpzmrc.test.djungle.io.ToDoProgressBindingModel_
import lpzmrc.test.djungle.io.ToDoTaskBindingModel_
import lpzmrc.test.djungle.io.ui.view.todos.model.ToDoView

class ToDosController : TypedEpoxyController<List<ToDoView>>() {

    override fun buildModels(data: List<ToDoView>?) {
        data?.forEach { item ->
            when (item.type) {
                ToDoView.Type.TASK -> {
                    (item as? ToDoView.ToDoTask)?.let { task ->
                        ToDoTaskBindingModel_()
                            .task(task)
                            .id(task.id)
                            .addTo(this)
                    }
                }
                ToDoView.Type.EMPTY -> (item as? ToDoView.ToDoEmpty)?.let { empty ->
                    ToDoEmptyBindingModel_()
                        .empty(empty)
                        .id(-ToDoView.Type.EMPTY.ordinal)
                        .addTo(this)
                }
                ToDoView.Type.PROGRESS -> (item as? ToDoView.ToDoProgress)?.let { progress ->
                    ToDoProgressBindingModel_()
                        .progress(progress)
                        .id(-ToDoView.Type.PROGRESS.ordinal)
                        .addTo(this)
                }
                ToDoView.Type.INVALIDATED -> (item as? ToDoView.ToDoInvalidated)?.let { invalidated ->
                    ToDoInvalidatedBindingModel_()
                        .invalidated(invalidated)
                        .id(-ToDoView.Type.INVALIDATED.ordinal)
                        .addTo(this)
                }
            }
        }
    }
}