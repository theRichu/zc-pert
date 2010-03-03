class GrantGgroupsController < ApplicationController
  # GET /grant_ggroups
  # GET /grant_ggroups.xml
  def index
    @grant_ggroups = GrantGgroup.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @grant_ggroups }
    end
  end

  # GET /grant_ggroups/1
  # GET /grant_ggroups/1.xml
  def show
    @grant_ggroup = GrantGgroup.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @grant_ggroup }
    end
  end

  # GET /grant_ggroups/new
  # GET /grant_ggroups/new.xml
  def new
    @grant_ggroup = GrantGgroup.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @grant_ggroup }
    end
  end

  # GET /grant_ggroups/1/edit
  def edit
    @grant_ggroup = GrantGgroup.find(params[:id])
  end

  # POST /grant_ggroups
  # POST /grant_ggroups.xml
  def create
    @grant_ggroup = GrantGgroup.new(params[:grant_ggroup])

    respond_to do |format|
      if @grant_ggroup.save
        flash[:notice] = 'GrantGgroup was successfully created.'
        format.html { redirect_to(@grant_ggroup) }
        format.xml  { render :xml => @grant_ggroup, :status => :created, :location => @grant_ggroup }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @grant_ggroup.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /grant_ggroups/1
  # PUT /grant_ggroups/1.xml
  def update
    @grant_ggroup = GrantGgroup.find(params[:id])

    respond_to do |format|
      if @grant_ggroup.update_attributes(params[:grant_ggroup])
        flash[:notice] = 'GrantGgroup was successfully updated.'
        format.html { redirect_to(@grant_ggroup) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @grant_ggroup.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /grant_ggroups/1
  # DELETE /grant_ggroups/1.xml
  def destroy
    @grant_ggroup = GrantGgroup.find(params[:id])
    @grant_ggroup.destroy

    respond_to do |format|
      format.html { redirect_to(grant_ggroups_url) }
      format.xml  { head :ok }
    end
  end
end
