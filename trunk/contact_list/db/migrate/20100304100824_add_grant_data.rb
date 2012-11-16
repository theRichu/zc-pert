class AddGrantData < ActiveRecord::Migration
  def self.up
    Grant.delete_all
    Grant.create(:grant_name=>'contact_list_index_nil', :grant_contrl=>'contact_list', :grant_action=>'index', :grant_target=>'nil', :grant_desc=>'contact_list_index_nil')
    Grant.create(:grant_name=>'staffs_new_nil', :grant_contrl=>'staffs', :grant_action=>'new', :grant_target=>'nil', :grant_desc=>'staffs_new_nil')
    Grant.create(:grant_name=>'staffs_create_nil', :grant_contrl=>'staffs', :grant_action=>'create', :grant_target=>'nil', :grant_desc=>'staffs_create_nil')
    Grant.create(:grant_name=>'staffs_edit_nil', :grant_contrl=>'staffs', :grant_action=>'edit', :grant_target=>'nil', :grant_desc=>'staffs_edit_nil')
    Grant.create(:grant_name=>'staffs_edit_current', :grant_contrl=>'staffs', :grant_action=>'edit', :grant_target=>'current', :grant_desc=>'staffs_edit_current')
    Grant.create(:grant_name=>'staffs_update_nil', :grant_contrl=>'staffs', :grant_action=>'update', :grant_target=>'nil', :grant_desc=>'staffs_update_nil')
    Grant.create(:grant_name=>'staffs_update_current', :grant_contrl=>'staffs', :grant_action=>'update', :grant_target=>'current', :grant_desc=>'staffs_update_current')
    Grant.create(:grant_name=>'staffs_edit_grant_nil', :grant_contrl=>'staffs', :grant_action=>'edit_grant', :grant_target=>'nil', :grant_desc=>'staffs_edit_grant_nil')
    Grant.create(:grant_name=>'staffs_update_grant_nil', :grant_contrl=>'staffs', :grant_action=>'update_grant', :grant_target=>'nil', :grant_desc=>'staffs_update_grant_nil')
    Grant.create(:grant_name=>'staffs_destroy_nil', :grant_contrl=>'staffs', :grant_action=>'destroy', :grant_target=>'nil', :grant_desc=>'staffs_destroy_nil')
    Grant.create(:grant_name=>'staffs_search_nil', :grant_contrl=>'staffs', :grant_action=>'search', :grant_target=>'nil', :grant_desc=>'staffs_search_nil')
    Grant.create(:grant_name=>'projects_index_nil', :grant_contrl=>'projects', :grant_action=>'index', :grant_target=>'nil', :grant_desc=>'projects_index_nil')
    Grant.create(:grant_name=>'projects_show_nil', :grant_contrl=>'projects', :grant_action=>'show', :grant_target=>'nil', :grant_desc=>'projects_show_nil')
    Grant.create(:grant_name=>'projects_new_nil', :grant_contrl=>'projects', :grant_action=>'new', :grant_target=>'nil', :grant_desc=>'projects_new_nil')
    Grant.create(:grant_name=>'projects_create_nil', :grant_contrl=>'projects', :grant_action=>'create', :grant_target=>'nil', :grant_desc=>'projects_create_nil')
    Grant.create(:grant_name=>'projects_edit_nil', :grant_contrl=>'projects', :grant_action=>'edit', :grant_target=>'nil', :grant_desc=>'projects_edit_nil')
    Grant.create(:grant_name=>'projects_update_nil', :grant_contrl=>'projects', :grant_action=>'update', :grant_target=>'nil', :grant_desc=>'projects_update_nil')
    Grant.create(:grant_name=>'projects_destroy_nil', :grant_contrl=>'projects', :grant_action=>'destroy', :grant_target=>'nil', :grant_desc=>'projects_destroy_nil')
    Grant.create(:grant_name=>'groups_index_nil', :grant_contrl=>'groups', :grant_action=>'index', :grant_target=>'nil', :grant_desc=>'groups_index_nil')
    Grant.create(:grant_name=>'groups_show_nil', :grant_contrl=>'groups', :grant_action=>'show', :grant_target=>'nil', :grant_desc=>'groups_show_nil')
    Grant.create(:grant_name=>'groups_new_nil', :grant_contrl=>'groups', :grant_action=>'new', :grant_target=>'nil', :grant_desc=>'groups_new_nil')
    Grant.create(:grant_name=>'groups_create_nil', :grant_contrl=>'groups', :grant_action=>'create', :grant_target=>'nil', :grant_desc=>'groups_create_nil')
    Grant.create(:grant_name=>'groups_edit_nil', :grant_contrl=>'groups', :grant_action=>'edit', :grant_target=>'nil', :grant_desc=>'groups_edit_nil')
    Grant.create(:grant_name=>'groups_update_nil', :grant_contrl=>'groups', :grant_action=>'update', :grant_target=>'nil', :grant_desc=>'groups_update_nil')
    Grant.create(:grant_name=>'groups_destroy_nil', :grant_contrl=>'groups', :grant_action=>'destroy', :grant_target=>'nil', :grant_desc=>'groups_destroy_nil')
    Grant.create(:grant_name=>'ggroups_index_nil', :grant_contrl=>'ggroups', :grant_action=>'index', :grant_target=>'nil', :grant_desc=>'ggroups_index_nil')
    Grant.create(:grant_name=>'ggroups_show_nil', :grant_contrl=>'ggroups', :grant_action=>'show', :grant_target=>'nil', :grant_desc=>'ggroups_show_nil')
    Grant.create(:grant_name=>'ggroups_new_nil', :grant_contrl=>'ggroups', :grant_action=>'new', :grant_target=>'nil', :grant_desc=>'ggroups_new_nil')
    Grant.create(:grant_name=>'ggroups_create_nil', :grant_contrl=>'ggroups', :grant_action=>'create', :grant_target=>'nil', :grant_desc=>'ggroups_create_nil')
    Grant.create(:grant_name=>'ggroups_edit_nil', :grant_contrl=>'ggroups', :grant_action=>'edit', :grant_target=>'nil', :grant_desc=>'ggroups_edit_nil')
    Grant.create(:grant_name=>'ggroups_update_nil', :grant_contrl=>'ggroups', :grant_action=>'update', :grant_target=>'nil', :grant_desc=>'ggroups_update_nil')
    Grant.create(:grant_name=>'ggroups_destroy_nil', :grant_contrl=>'ggroups', :grant_action=>'destroy', :grant_target=>'nil', :grant_desc=>'ggroups_destroy_nil')
    Grant.create(:grant_name=>'grants_index_nil', :grant_contrl=>'grants', :grant_action=>'index', :grant_target=>'nil', :grant_desc=>'grants_index_nil')
    Grant.create(:grant_name=>'grants_show_nil', :grant_contrl=>'grants', :grant_action=>'show', :grant_target=>'nil', :grant_desc=>'grants_show_nil')
    Grant.create(:grant_name=>'grants_new_nil', :grant_contrl=>'grants', :grant_action=>'new', :grant_target=>'nil', :grant_desc=>'grants_new_nil')
    Grant.create(:grant_name=>'grants_create_nil', :grant_contrl=>'grants', :grant_action=>'create', :grant_target=>'nil', :grant_desc=>'grants_create_nil')
    Grant.create(:grant_name=>'grants_edit_nil', :grant_contrl=>'grants', :grant_action=>'edit', :grant_target=>'nil', :grant_desc=>'grants_edit_nil')
    Grant.create(:grant_name=>'grants_update_grant_nil', :grant_contrl=>'grants', :grant_action=>'update_grant', :grant_target=>'nil', :grant_desc=>'grants_update_grant_nil')
    Grant.create(:grant_name=>'grants_destroy_nil', :grant_contrl=>'grants', :grant_action=>'destroy', :grant_target=>'nil', :grant_desc=>'grants_destroy_nil')
    Grant.create(:grant_name=>'clenums_index_nil', :grant_contrl=>'clenums', :grant_action=>'index', :grant_target=>'nil', :grant_desc=>'clenums_index_nil')
    Grant.create(:grant_name=>'clenums_show_nil', :grant_contrl=>'clenums', :grant_action=>'show', :grant_target=>'nil', :grant_desc=>'clenums_show_nil')
    Grant.create(:grant_name=>'clenums_new_nil', :grant_contrl=>'clenums', :grant_action=>'new', :grant_target=>'nil', :grant_desc=>'clenums_new_nil')
    Grant.create(:grant_name=>'clenums_create_nil', :grant_contrl=>'clenums', :grant_action=>'create', :grant_target=>'nil', :grant_desc=>'clenums_create_nil')
    Grant.create(:grant_name=>'clenums_edit_nil', :grant_contrl=>'clenums', :grant_action=>'edit', :grant_target=>'nil', :grant_desc=>'clenums_edit_nil')
    Grant.create(:grant_name=>'clenums_update_nil', :grant_contrl=>'clenums', :grant_action=>'update', :grant_target=>'nil', :grant_desc=>'clenums_update_nil')
    Grant.create(:grant_name=>'clenums_destroy_nil', :grant_contrl=>'clenums', :grant_action=>'destroy', :grant_target=>'nil', :grant_desc=>'clenums_destroy_nil')
    Grant.create(:grant_name=>'staffs_login_nil', :grant_contrl=>'staffs', :grant_action=>'login', :grant_target=>'nil', :grant_desc=>'staffs_login_nil')
    Grant.create(:grant_name=>'staffs_logout_nil', :grant_contrl=>'staffs', :grant_action=>'logout', :grant_target=>'nil', :grant_desc=>'staffs_logout_nil')
    Grant.create(:grant_name=>'histories_index_nil', :grant_contrl=>'histories', :grant_action=>'index', :grant_target=>'nil', :grant_desc=>'histories_index_nil')
    Grant.create(:grant_name=>'histories_show_nil', :grant_contrl=>'histories', :grant_action=>'show', :grant_target=>'nil', :grant_desc=>'histories_show_nil')
    Grant.create(:grant_name=>'administroter_index_nil', :grant_contrl=>'administroter', :grant_action=>'index', :grant_target=>'nil', :grant_desc=>'administroter_index_nil')
  end

  def self.down
    Grant.delete_all
  end
end