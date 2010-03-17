class ContactListController < ApplicationController
  # GET /staffs
  # GET /staffs.xml
  def index
    if request.xhr? then
      render(:layout => false)
    end
  end

end
