class AddGrantForDefaultGgroup < ActiveRecord::Migration
  def self.up
    ggroup = Ggroup.find_by_gg_name Ggroup::DEFAULT
    
    grant1 = Grant.find_by_grant_name 'contact_list_index_nil'
    grant1.ggroups << ggroup
    #ggroup.grants << grant1 // this will add one duplication to the database

    grant2 = Grant.find_by_grant_name 'staffs_search_nil'
    grant2.ggroups << ggroup

    grant3 = Grant.find_by_grant_name 'projects_index_nil'
    grant3.ggroups << ggroup

    grant4 = Grant.find_by_grant_name 'projects_show_nil'
    grant4.ggroups << ggroup

    grant5 = Grant.find_by_grant_name 'groups_index_nil'
    grant5.ggroups << ggroup

    grant6 = Grant.find_by_grant_name 'groups_show_nil'
    grant6.ggroups << ggroup

    grant7 = Grant.find_by_grant_name 'staffs_login_nil'
    grant7.ggroups << ggroup

    grant8 = Grant.find_by_grant_name 'staffs_logout_nil'
    grant8.ggroups << ggroup
  end

  def self.down
  end
end
