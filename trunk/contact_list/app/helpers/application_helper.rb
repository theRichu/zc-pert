# Methods added to this helper will be available to all templates in the application.
module ApplicationHelper
  def menu_item_tag(name, options = {}, html_options = nil)
    if allowed? 
      name = image_tag(options[:icon], :align=>'absmiddle', :border=>'0') +"&nbsp;"+ name if options[:icon];
      options.delete :icon
      link_to_remote name, options, html_options
    end
  end

  def board_menu_item_tag(name, options = {}, html_options = nil)
    if options[:ids] 
      options[:condition] = 'check_selected()'
      options[:url][:id] = 0
      options[:with] = "'ids=' + all_selected_id()" if options[:ids] == :all
      options[:with] = "'ids=' + first_selected_id()" if options[:ids] == :first
      options.delete :ids
    end
    options[:update] = 'board_content'
    menu_item_tag name, options, html_options
  end

  def board_menu
    menu_items = Array.new
    action = params[:action]
    return menu_items unless action
    if action == 'index'
      menu_items << board_menu_item_tag('Create', :icon=>'new.png', :url=>{:action=>'new'}, :method=>:get)
      menu_items << board_menu_item_tag('Delete', :icon=>'destroy.png', :url=>{:action=>'destroy'}, :ids=>:all, :method => :delete, :confirm => 'Are you sure?')
      menu_items << board_menu_item_tag('Edit', :icon=>'edit.png', :url=>{:action=>'edit'}, :ids=>:first, :method => :get)
      menu_items << board_menu_item_tag('View', :icon=>'show.png', :url=>{:action=>'show'}, :ids=>:first, :method => :get)
    end
    if action == 'new'
      menu_items << board_menu_item_tag('Save', :icon=>'save.png', :url=>{:action=>'create'}, :method => :post, :submit=>'new_form')
    end
    if action == 'edit'
      menu_items << board_menu_item_tag('Save', :icon=>'save.png', :url=>{:action=>'update'}, :method => :put, :submit=>'edit_form')
    end
    if action == 'new' || action == 'edit'
      menu_items << board_menu_item_tag('Cancel', :icon=>'cancel.png', :url=>{:action=>'index'}, :method => :get)
    end
    if action == 'show'
      menu_items << board_menu_item_tag('Back', :icon=>'back.png', :url=>{:action=>'index'}, :method => :get)
    end
    menu_items
  end

  def login_tag
    unless session[:staff_id] 
      link_to_remote 'Login', :controller=>'staff', :action=>'login'
    end
  end

  def logout_tag
    if session[:staff_id] 
      link_to_remote 'Logout', :controller=>'staff', :action=>'logout'
    end
  end

  def options_project
    Project.find(:all, :conditions=>"project_name != '#{Project::DEFAULT}'").map {|p| [p.project_name, p.id]}
  end

  def options_groups_for_first_project
    project_id = Project.find(:first, :conditions=>"project_name != '#{Project::DEFAULT}'").id
    Group.find(:all, :conditions=>"project_id = '#{project_id}'").map { |g| [g.group_name, g.id] }
  end

  def public_projects
    Project.find :all, :conditions=>"project_name != '#{Project::DEFAULT}'"
  end

  def options_category
    Clenum.find(:all, :conditions=>'category_id is null').map {|c| [ c.enum_text, c.id ] }
  end

  def options_ggroup
    Ggroup.find(:all, :conditions=>"gg_name !='#{Ggroup::DEFAULT}'").map {|gg| [gg.gg_name, gg.id]}
  end

  def options_staff
    Staff.find(:all, :conditions=>"en_lastname!=':default'").map { |s| [s.cn_lastname + s.cn_firstname + ' (' + s.en_firstname + ' ' + s.en_lastname + ')', s.id] }
  end

  def options_enum category
    return nil unless category
    category = Clenum.find_by_enum_text(category)
    if category 
      clenums = Clenum.find(:all, :conditions=>"category_id = #{category.id}", :order=>'enum_text')
      clenums.map { |e| [e.enum_text, e.id] }
    end
  end

  def ajax_paginate_tag entries, cols=1
    if entries
      paginate = will_paginate_remote entries, :update=>'board_content', :url=>nil, :separator=>" | "
      unless paginate
        paginate = '<div class="pagination"><span class="disabled prev_page">« Previous</span> | <span class="current">1</span> | <span class="disabled next_page">Next »</span></div>'
      end
      tag = '<tr><td colspan="'+cols.to_s+'" align="center">'
      tag += paginate
      tag += '</td></tr>'
      return tag
    else
      return ''
    end
  end
  
end
