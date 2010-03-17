class AddDefaultProjectAndGroup < ActiveRecord::Migration
  def self.up
    Group.delete_all
    Project.delete_all
    project = Project.create(:project_name=>Project::DEFAULT, :project_desc=>Project::DEFAULT)
    Group.create(:project=>project, :group_name=>Group::DEFAULT, :group_desc=>Group::DEFAULT)
  end

  def self.down
    Group.delete_all
    Project.delete_all
  end
end
