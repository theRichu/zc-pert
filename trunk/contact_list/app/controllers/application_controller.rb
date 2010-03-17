# Filters added to this controller apply to all controllers in the application.
# Likewise, all the methods added will be available for all controllers.

class ApplicationController < ActionController::Base
  # include all helpers, all the time
  # helper :all

  protect_from_forgery # See ActionController::RequestForgeryProtection for details

  # Scrub sensitive parameters from your log
  # filter_parameter_logging :password

  before_filter :authorize

  protected
  def authorize
    unless allowed? then
      session[:staff_ggoup_id] = nil
      redirect_to :controller => 'contact_list', :action=>'index'
    end
  end

  def allowed?
    true
    #    unless session[:staff_ggoup_id] then
    #      # if the user is not login or the user is not login and firsst time to invoke the action
    #      public_gg = Ggroup.find_by_gg_name Ggroup::DEFAULT
    #      session[:staff_ggoup_id] = public_gg.id
    #    else
    #      # if the user has been login or the staff_ggoup_id has been setted into the session
    #      public_gg = Ggroup.find session[:staff_ggoup_id]
    #    end
    #    # check that whether the user can access the action
    #    # get the controller and the actoin user desired
    #    req_contrl = request.path_parameters[:controller]
    #    req_action = request.path_parameters[:action]
    #
    #    grant = nil
    #    public_gg.grants.each do |g|
    #      if g.grant_contrl == req_contrl && g.grant_action == req_action then
    #        grant = g
    #        break
    #      end
    #    end
    #
    #    allowed = false
    #    if grant then
    #      if grant.grant_target == Grant::NIL then
    #        allowed = true
    #      elsif grant.grant_target == Grant::CURRENT then
    #        # the opreation just can do by current login user
    #        if session[:staff_id] && request[:id] then
    #          allowed = true
    #        end
    #      end
    #    end
    #    allowed
  end

  helper_method :allowed?

end
