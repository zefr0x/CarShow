package carshow;

interface Searchable {
    /*
     * This method will be called by filtering mechanizm. If it return true - item
     * stay in result, otherwise item droped.
     */
    boolean passSearchTerm(String filterTerm);
}
