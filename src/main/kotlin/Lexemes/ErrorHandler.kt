package Lexemes

object ErrorHandler {
    private var error: Id_errors = Id_errors.NON_ERROR
    private var begin_error = -1
    private var end_error = -1
    fun setError(error: Id_errors, fail_lexeme: Lexeme) {
        ErrorHandler.error = error
        begin_error = fail_lexeme.begin
        end_error = fail_lexeme.end
    }

    fun setError(error: Id_errors, begin: Int, end: Int) {
        ErrorHandler.error = error
        begin_error = begin
        end_error = end
    }

    fun getError(): Id_errors {
        return error
    }

    fun get_begin_error(): Int {
        return begin_error
    }

    fun get_end_error(): Int {
        return end_error
    }

    fun set_default() {
        error = Id_errors.NON_ERROR
        begin_error = -1
        end_error = -1
    }
}