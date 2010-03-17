class StaffsController < ApplicationController
  layout 'board_content'
  
  # GET /staffs
  # GET /staffs.xml
  def index
    if params[:group_id] then
      @group = '=' + params[:group_id]
    else
      unless params[:project_id] && project = Project.find(params[:project_id])
        project = Project.find :first, :conditions=>"project_name != '#{Project::DEFAULT}'"
      end
      @group ='IN(' + project.groups.map {|g| g.id}.join(',') + ')'
    end
    
    @order_by = params[:order_by] || 'en_firstname'

    @staffs = Staff.paginate :conditions=>"group_id #{@group}", :page=>params[:page], :per_page=>12, :order=>@order_by

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @staffs }
    end
  end

  # GET /staffs/1
  # GET /staffs/1.xml
  def show
    @staff = Staff.find(params[:ids])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @staff }
    end
  end

  # GET /staffs/new
  # GET /staffs/new.xml
  def new
    @staff = Staff.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @staff }
    end
  end

  # GET /staffs/1/edit
  def edit
    @staff = Staff.find(params[:ids])
  end

  # GET /staffs/1/edit_grant
  def edit_grant
    @staff = Staff.find(params[:id])
  end

  # PUT /staffs/1/update_grant
  # PUT /staffs/1/update_grant.xml
  def update_grant
    @staff = Staff.find(params[:id])

    respond_to do |format|
      if @staff.update_attributes(params[:staff])
        flash[:notice] = 'Staff was successfully updated.'
        format.html { redirect_to(@staff) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @staff.errors, :status => :unprocessable_entity }
      end
    end
  end

  # POST /staffs
  # POST /staffs.xml
  def create
    params.delete 'project'
    @staff = Staff.new(params[:staff])

    respond_to do |format|
      if @staff.save
        flash[:notice] = 'Staff was successfully created.'
        format.html { redirect_to(:action=>'show', :ids=>@staff) }
        format.xml  { render :xml => @staff, :status => :created, :location => @staff }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @staff.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /staffs/1
  # PUT /staffs/1.xml
  def update
    @staff = Staff.find(params[:ids])

    respond_to do |format|
      if @staff.update_attributes(params[:staff])
        flash[:notice] = 'Staff was successfully updated.'
        format.html { redirect_to(:action=>'show', :ids=>@staff) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @staff.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /staffs/1
  # DELETE /staffs/1.xml
  def destroy
    Staff.destroy params[:ids].split(',')

    respond_to do |format|
      format.html { redirect_to(staffs_url) }
      format.xml  { head :ok }
    end
  end

  # POST /staffs/login
  # POST /staffs/login.xml
  def login

  end

  # POST /staffs/logout
  # POST /staffs/logout.xml
  def logout
    
  end
end
