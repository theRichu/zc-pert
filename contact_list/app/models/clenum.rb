class Clenum < ActiveRecord::Base
  TYPE_ENUM='enum'
  TYPE_CATE='category'
  TEAM	=	'Team'
  WAVE	=	'Wave'
  PRODUCT	=	'Product'
  BASE	=	'Base'
  DEPARTMENT	=	'Department'
  SITE	=	'Site'
  ROLE2	=	'Role 2'
  ROLE1	=	'Role 1'

  # Self-referential Joins
  belongs_to :category, :class_name => 'Clenum', :foreign_key => 'category_id'
  has_many :enums, :class_name => 'Clenum', :foreign_key => 'category_id'

  # relationship with staff table
  has_many :staffs_wave, :class_name=>'Staff', :foreign_key=>'wave_id'
  has_many :staffs_team, :class_name=>'Staff', :foreign_key=>'team_id'
  has_many :staffs_role1, :class_name=>'Staff', :foreign_key=>'role1_id'
  has_many :staffs_role2, :class_name=>'Staff', :foreign_key=>'role2_id'
  has_many :staffs_site, :class_name=>'Staff', :foreign_key=>'site_id'
  has_many :staffs_dept, :class_name=>'Staff', :foreign_key=>'dept_id'
  has_many :staffs_base, :class_name=>'Staff', :foreign_key=>'base_id'
  has_many :staffs_product, :class_name=>'Staff', :foreign_key=>'product_id'
end
