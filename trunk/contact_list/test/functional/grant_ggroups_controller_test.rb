require 'test_helper'

class GrantGgroupsControllerTest < ActionController::TestCase
  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:grant_ggroups)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create grant_ggroup" do
    assert_difference('GrantGgroup.count') do
      post :create, :grant_ggroup => { }
    end

    assert_redirected_to grant_ggroup_path(assigns(:grant_ggroup))
  end

  test "should show grant_ggroup" do
    get :show, :id => grant_ggroups(:one).to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => grant_ggroups(:one).to_param
    assert_response :success
  end

  test "should update grant_ggroup" do
    put :update, :id => grant_ggroups(:one).to_param, :grant_ggroup => { }
    assert_redirected_to grant_ggroup_path(assigns(:grant_ggroup))
  end

  test "should destroy grant_ggroup" do
    assert_difference('GrantGgroup.count', -1) do
      delete :destroy, :id => grant_ggroups(:one).to_param
    end

    assert_redirected_to grant_ggroups_path
  end
end
