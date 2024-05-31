/* 仅从低版本升级到v413的时候需要刷此脚本 */
/* 如果是首次安装，或者是更高版本，不需要执行此脚本 */

use code_review;

alter table t_comment_column add constraint t_comment_column_uk unique (column_code);


INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('gitBranchName', false, false, false, 50, 'TEXT', false, true, true, true, true, true, 200, 'gitBranchName', 3, true, false, null);
INSERT INTO t_comment_column (column_code, editable_in_add_page, editable_in_edit_page, editable_in_confirm_page, excel_column_width, input_type, required, show_in_add_page, show_in_edit_page, show_in_confirm_page, show_in_idea_table, show_in_web_table, web_table_column_width, show_name, sort_index, support_in_excel, system_initialization, dict_collection_code) VALUES ('gitRepositoryName', false, false, false, 50, 'TEXT', false, true, true, true, true, true, 200, 'gitRepositoryName', 2, true, false, null);

commit;
