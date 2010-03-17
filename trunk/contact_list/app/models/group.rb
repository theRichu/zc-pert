class Group < ActiveRecord::Base
  DEFAULT=':public'
  belongs_to :project
  has_many :staffs
end
