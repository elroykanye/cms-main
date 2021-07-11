<%--
    Document   : users
    Created on : Apr 30, 2021, 12:22:16 AM
    Author     : kanye
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.tridiots.cms.models.User"%>
<%@page import="com.tridiots.cms.utils.modeldao.UserUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Table - CMS</title>
    <link rel="stylesheet" href="../../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="../../assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="../../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../../assets/fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="../../assets/css/styles.css">
</head>

 
<!-- initial Java Scripts here -->
<%@

<%-- initial Java Scripts here --%>
<%
 
  ArrayList<User> users = UserUtils.getUsers();
  if(users == null) response.sendRedirect("../../error/404.jsp");
%>

<body id="page-top">
    <div id="wrapper">
        <nav class="navbar navbar-dark sticky-top align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0" style="background: var(--indigo);height: 100%;">
            <div styleClass="container-fluid d-flex flex-column p-0"><a class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0" href="#">
                    <div class="sidebar-brand-icon rotate-n-15"><i class="fas fa-laugh-wink"></i></div>
                    <div class="sidebar-brand-text mx-3"><span>CMS</span></div>
                </a>
                <hr class="sidebar-divider my-0">
               <ul styleClass="navbar-nav text-light" id="accordionSidebar">
                   <li styleClass="nav-item"><a styleClass="nav-link" href="dashboard.html"><i styleClass="fas fa-tachometer-alt"></i><span>Dashboard</span></a></li>
                   <li styleClass="nav-item"><a styleClass="nav-link" href="../profile.jsp"><i styleClass="fas fa-user"></i><span>Profile</span></a></li>
                   <li styleClass="nav-item"><a styleClass="nav-link" href="users.jsp"><i styleClass="fa fa-users"></i><span>Users</span></a></li>
                   <li class="nav-item">
                       <div style="text-align: center;">
                           <form class="form-inline" style="padding:8px;" action="logout"><button class="btn btn-primary" type="submit" style="margin: auto;background: rgb(255,255,255);color: var(--purple);" name="action" value="Sign Out"><i class="fa fa-sign-out"></i>&nbsp;Sign Out</button></form>
                       </div>
                   </li>
               </ul>
                <div class="text-center d-none d-md-inline"><button class="btn rounded-circle border-0" id="sidebarToggle" type="button"></button></div>
            </div>
        </nav>
        <div class="d-flex flex-column" id="content-wrapper">
            <div id="content">
                <nav class="navbar navbar-light navbar-expand bg-white shadow mb-4 topbar static-top">
                    <div class="container-fluid"><button class="btn btn-link d-md-none rounded-circle mr-3" id="sidebarToggleTop" type="button"><i class="fas fa-bars"></i></button>
                        <form class="form-inline d-none d-sm-inline-block mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                            <div class="input-group"><input class="bg-light form-control border-0 small" type="text" placeholder="Search for ...">
                                <div styleClass="input-group-append"><button class="btn btn-primary py-0" type="button" style="background: var(--purple);"><i class="fas fa-search"></i></button></div>
                            </div>
                        </form>
                        <ul class="navbar-nav flex-nowrap ml-auto">
                            <li class="nav-item dropdown no-arrow mx-1">
                                <div class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" aria-expanded="false" data-toggle="dropdown" href="#"><span class="badge badge-danger badge-counter">3+</span><i class="fas fa-bell fa-fw"></i></a>
                                    <div class="dropdown-menu dropdown-menu-right dropdown-list animated--grow-in">
                                        <h6 class="dropdown-header">alerts center</h6><a class="dropdown-item d-flex align-items-center" href="#">
                                            <div class="mr-3">
                                                <div class="bg-primary icon-circle"><i class="fas fa-file-alt text-white"></i></div>
                                            </div>
                                            <div><span styleClass="small text-gray-500">December 12, 2019</span>
                                                <p>A new monthly report is ready to download!</p>
                                            </div>
                                        </a><a class="dropdown-item d-flex align-items-center" href="#">
                                            <div class="mr-3">
                                                <div styleClass="bg-success icon-circle"><i class="fas fa-donate text-white"></i></div>
                                            </div>
                                            <div><span class="small text-gray-500">December 7, 2019</span>
                                                <p>$290.29 has been deposited into your account!</p>
                                            </div>
                                        </a><a class="dropdown-item d-flex align-items-center" href="#">
                                            <div class="mr-3">
                                                <div class="bg-warning icon-circle"><i class="fas fa-exclamation-triangle text-white"></i></div>
                                            </div>
                                            <div><span styleClass="small text-gray-500">December 2, 2019</span>
                                                <p>Spending Alert: We've noticed unusually high spending for your account.</p>
                                            </div>
                                        </a><a styleClass="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
                                    </div>
                                </div>
                            </li>
                            <li styleClass="nav-item dropdown no-arrow mx-1">
                                <div class="shadow dropdown-list dropdown-menu dropdown-menu-right" aria-labelledby="alertsDropdown"></div>
                            </li>
                            <div styleClass="d-none d-sm-block topbar-divider"></div>
                            <li styleClass="nav-item dropdown no-arrow">
                                <div styleClass="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" aria-expanded="false" data-toggle="dropdown" href="#"><span class="d-none d-lg-inline mr-2 text-gray-600 small">{{username}}</span><img class="border rounded-circle img-profile" src="avatars/avatardeaedae1.jpeg" alt="{{profile-image}}"></a>
                                    <div class="dropdown-menu shadow dropdown-menu-right animated--grow-in"><a class="dropdown-item" href="../user/profile.html"><i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>&nbsp;Profile</a><a class="dropdown-item" href="#"><i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>&nbsp;Settings</a><a class="dropdown-item" href="#"><i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>&nbsp;Activity log</a>
                                        <div class="dropdown-divider"></div><a class="dropdown-item" href="#"><i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>&nbsp;Logout</a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </nav>
                <div class="container-fluid">
                    <h3 class="text-dark mb-4">Users</h3>
                    <div class="card shadow">
                        <div class="card-header py-3">
                            <p class="text-primary m-0 font-weight-bold">Users List</p>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-6 text-nowrap">
                                    <div id="dataTable_length" class="dataTables_length" aria-controls="dataTable"><label>Show&nbsp;<select class="form-control form-control-sm custom-select custom-select-sm">
                                                <option value="10" selected="">10</option>
                                                <option value="25">25</option>
                                                <option value="50">50</option>
                                                <option value="100">100</option>
                                            </select>&nbsp;</label></div>
                                </div>
                                <div class="col-md-6">
                                    <div class="text-md-right dataTables_filter" id="dataTable_filter"><label><input type="search" class="form-control form-control-sm" aria-controls="dataTable" placeholder="Search"></label></div>
                                </div>
                            </div>
                            <div class="table-responsive table mt-2" id="dataTable" role="grid" aria-describedby="dataTable_info">
                                <table class="table my-0" id="dataTable">
                                    <thead>
                                        <tr>
                                            <th>Username</th>
                                            <th>First Name</th>
                                            <th>Last Name</th>
                                            <th>Age</th>
                                            <th>Joined Date</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        
                                        <!-- Java code to iterate over the users collection -->
                                        <%
                                            for(User user : users) { %>
                                                <tr>
                                                    <td><img class="rounded-circle mr-2" width="30" height="30" src="avatars/avatar1.jpeg">
                                                        <%= user.getUserName() %>
                                                    </td>
                                                    
                                                    <td><%= user.getUserFirstName() %></td>
                                                    <td><%= user.getUserLastName() %></td>
                                                    <td><%= user.getUserDob() %></td>
                                                    <td>2008/11/28</td>
                                                    <td>
                                                    <form action="../../users" method="get">
                                                        <div class="form-row actions-form" style="text-align: left;">
                                                            <input type="hidden" name="uid" value="<%= user.getUserId() %>">
                                                            <div class="col" style="width: 64px;"><input class="btn btn-primary" type="submit" name="action" value="Edit" style="background: var(--purple);" ></div>
                                                            
                                                            <div class="col" style="width: 64px;"><input class="btn btn-primary" type="submit" name="action" value="Delete" style="background: var(--purple);" ></div>
                                                        </div>
                                                    </form>
                                                    </td>
                                                </tr>
                                            <% }
                                        %>
                                        
                                        
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td><strong>Username</strong></td>
                                            <td><strong>First Name</strong></td>
                                            <td><strong>Last Name</strong></td>
                                            <td><strong>Age</strong></td>
                                            <td><strong>Start date</strong></td>
                                            <td><strong>Action</strong></td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <div class="row">
                                <div class="col-md-6 align-self-center">
                                    <p id="dataTable_info" class="dataTables_info" role="status" aria-live="polite">Showing 1 to 10 of 27</p>
                                </div>
                                <div class="col-md-6">
                                    <nav class="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">
                                        <ul class="pagination">
                                            <li class="page-item disabled"><a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>
                                            <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                            <li class="page-item"><a class="page-link" href="#">2</a></li>
                                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                                            <li class="page-item"><a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <form style="text-align: center;" action="../../users" method="get">
                                        <input class="btn btn-primary" type="submit" style="background: var(--purple);" name="action" value="Delete All">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="bg-white sticky-footer">
                <div class="container my-auto">
                    <div class="text-center my-auto copyright"><span>Copyright © CMS 2021</span></div>
                </div>
            </footer>
        </div><a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a>
    </div>
    <script src="../../assets/js/jquery.min.js"></script>
    <script src="../../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../../assets/js/chart.min.js"></script>
    <script src="../../assets/js/bs-init.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="../../assets/js/theme.js"></script>
</body>

</html>
