class Ggroup < ActiveRecord::Base
  DEFAULT = ':public'
  STAFF = 'staff'
  PMO = 'PM & PMO'
  has_many :staffs
  has_and_belongs_to_many :grants
end
