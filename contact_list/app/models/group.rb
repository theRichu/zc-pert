class Group < ActiveRecord::Base
  belongs_to :project
  has_many :staffs
end
