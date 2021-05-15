<%--
    Document   : submission
    Created on : Apr 30, 2021, 12:22:16 AM
    Author     : kanye
--%>
<%@ page import="com.tridiots.cms.models.Submission" %>
<%@ page import="com.tridiots.cms.models.Grade" %>
<%@ page import="com.tridiots.cms.models.User" %>
<%@ page import="com.tridiots.cms.models.Contestant" %>
<%@ page import="com.tridiots.cms.models.Judge" %>
<%@ page import="com.tridiots.cms.utils.modeldao.GradeUtils" %>
<%@ page import="com.tridiots.cms.utils.modeldao.SubmissionUtils" %>
<%@ page import="com.tridiots.cms.utils.modeldao.ContestantUtils" %>
<%@ page import="com.tridiots.cms.utils.modeldao.UserUtils" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
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

<%
Submission thisSubmission = (Submission) request.getAttribute("submission");
Contestant thisContestant = (Contestant) request.getAttribute("contestant");
Judge loggedInJudge = (Judge) request.getSession().getAttribute("loggedInJudge");

%>

<body id="page-top">
    <div id="wrapper">
        <nav class="navbar navbar-dark sticky-top align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0" style="background: var(--indigo);height: 100%;">
            <div class="container-fluid d-flex flex-column p-0"><a class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0" href="#">
                    <div class="sidebar-brand-icon rotate-n-15"><i class="fas fa-book-open"></i></div>
                    <div class="sidebar-brand-text mx-3"><span>CMS</span></div>
                </a>
                <hr class="sidebar-divider my-0">
                <ul class="navbar-nav text-light" id="accordionSidebar">
                    <li class="nav-item"><a class="nav-link" href="/user/judge/dashboard.html"><i class="fas fa-tachometer-alt"></i><span>Dashboard</span></a></li>
                    <li class="nav-item"><a class="nav-link" href="/user/profile.html"><i class="fas fa-user"></i><span>Profile</span></a></li>
                    <li class="nav-item"><a class="nav-link" href="/user/judge/contestants.html"><i class="fa fa-users"></i><span>Contestants</span></a></li>
                    <li class="nav-item"><a class="nav-link" href="/user/judge/submissions.html"><i class="fas fa-table"></i><span>Submissions</span></a></li>
                    <li class="nav-item">
                        <div style="text-align: center;">
                            <form class="form-inline" style="padding:8px;"><button class="btn btn-primary" type="submit" style="margin: auto;background: rgb(255,255,255);color: var(--purple);" name="action" value="Sign Out"><i class="fa fa-sign-out"></i>&nbsp;Sign Out</button></form>
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
                                <div class="input-group-append"><button class="btn btn-primary py-0" type="button" style="background: var(--purple);"><i class="fas fa-search"></i></button></div>
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
                                            <div><span class="small text-gray-500">December 12, 2019</span>
                                                <p>A new monthly report is ready to download!</p>
                                            </div>
                                        </a><a class="dropdown-item d-flex align-items-center" href="#">
                                            <div class="mr-3">
                                                <div class="bg-success icon-circle"><i class="fas fa-donate text-white"></i></div>
                                            </div>
                                            <div><span class="small text-gray-500">December 7, 2019</span>
                                                <p>$290.29 has been deposited into your account!</p>
                                            </div>
                                        </a><a class="dropdown-item d-flex align-items-center" href="#">
                                            <div class="mr-3">
                                                <div class="bg-warning icon-circle"><i class="fas fa-exclamation-triangle text-white"></i></div>
                                            </div>
                                            <div><span class="small text-gray-500">December 2, 2019</span>
                                                <p>Spending Alert: We've noticed unusually high spending for your account.</p>
                                            </div>
                                        </a><a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
                                    </div>
                                </div>
                            </li>
                            <li class="nav-item dropdown no-arrow mx-1">
                                <div class="shadow dropdown-list dropdown-menu dropdown-menu-right" aria-labelledby="alertsDropdown"></div>
                            </li>
                            <div class="d-none d-sm-block topbar-divider"></div>
                            <li class="nav-item dropdown no-arrow">
                                <div class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" aria-expanded="false" data-toggle="dropdown" href="#"><span class="d-none d-lg-inline mr-2 text-gray-600 small">{{username}}</span><img class="border rounded-circle img-profile" src="avatars/avatardeaedae1.jpeg" alt="{{profile-image}}"></a>
                                    <div class="dropdown-menu shadow dropdown-menu-right animated--grow-in"><a class="dropdown-item" href="../profile.html"><i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>&nbsp;Profile</a><a class="dropdown-item" href="#"><i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>&nbsp;Settings</a><a class="dropdown-item" href="#"><i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>&nbsp;Activity log</a>
                                        <div class="dropdown-divider"></div><a class="dropdown-item" href="#"><i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>&nbsp;Logout</a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </nav>
                <div class="container-fluid">
                    <h3 class="text-dark mb-4">Submission</h3>
                    <div class="card shadow">
                        <div class="card-header py-3">
                            <p class="text-primary m-0 font-weight-bold"><%= thisContestant.getUserFirstName().concat(" ").concat(thisContestant.getUserLastName()) %></p>
                        </div>
                        <div class="card-body">
                            <div>
                                <h3><%=thisSubmission.getSubmissionPoemTitle() %></h3>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Kom version</h4>
                                            <p class="card-text"><%=thisSubmission.getSubmissionPoemKom() %></p><a class="card-link" href="#">Link</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">English</h4>
                                            <p class="card-text"><%=thisSubmission.getSubmissionPoemEn() %></p><a class="card-link" href="#">Link</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="padding-top:32px; padding-bottom:32px;">
                            	<%
                            		if(loggedInJudge.getJudgeLevel() == 404) {
                            			int jid = loggedInJudge.getJudgeId();
                            			int subid = thisSubmission.getSubmissionId();
                            			int conid = thisContestant.getContestantId();
                            			Grade grade = GradeUtils.getGrade(jid, subid);
                            			
                            			%>
                            				<div class="col">
                                    			<form class="text-center" action="submissions" method="post">
                                    				<input type="hidden" name="jid" value="<%=jid %>">
                                    				<input type="hidden" name="subid" value="<%=subid %>">
                                    				<input type="hidden" name="conid" value="<%=conid %>">
                                        			<div class="form-group score-input-form-group"><input class="form-control" type="number" name="score" value="<%=grade.getSubmissionGrade() == null? 0 : grade.getSubmissionGrade() %>" min="0" max="100"></div>
                                        			
                                        			<%
                                        			String message = (String) request.getAttribute("message");
                                        			if(message != null) {
                                        				%>
                                        					<div class="form-group score-input-form-group"><span><%=message %></span></div>
                                        				<% } %>
                                        			
                                        			<div class="form-group score-submit-form-group"><button class="btn btn-primary btn-user" type="submit" name="action" value="submitScore" style="background: var(--indigo);">Submit Score</button></div>
                                    			</form>
                                			</div>
                            			
                            			<% 
                            		} else if(loggedInJudge.getJudgeLevel() == 202) {
                            			%>
                            				<div class="col">
                                    			<form class="text-center">
                                        			<div class="form-group score-input-form-group"><input class="form-control" type="text"></div>
                                        			<div class="form-group score-submit-form-group"><button class="btn btn-primary btn-user" type="button" style="background: var(--indigo);">Submit Score</button></div>
                                    			</form>
                                			</div>
                                			<div class="col">
                                    			<form class="text-center">
                                        			<div class="form-group score-input-form-group"><input class="form-control" type="text"></div>
                                        			<div class="form-group score-submit-form-group"><button class="btn btn-primary btn-user" type="button" style="background: var(--indigo);">Submit Score</button></div>
                                    			</form>
                                			</div>
                                			<div class="col">
                                    			<form class="text-center">
                                        			<div class="form-group score-input-form-group"><input class="form-control" type="text"></div>
                                       			 	<div class="form-group score-submit-form-group"><button class="btn btn-primary btn-user" type="button" style="background: var(--indigo);">Submit Score</button></div>
                                    			</form>
                                			</div>
                            			<%
                            		} else {
                            			request.getSession().setAttribute("errorMessage", "Fake judge account");
                            			request.getRequestDispatcher("/logout").forward(request, response);
                            		}
                            	%>
                                
                                
                                <!-- 
                                <div class="col">
                                    <form class="text-center">
                                        <div class="form-group score-input-form-group"><input class="form-control" type="text"></div>
                                        <div class="form-group score-submit-form-group"><button class="btn btn-primary btn-user" type="button" style="background: var(--indigo);">Submit Score</button></div>
                                    </form>
                                </div>
                                <div class="col">
                                    <form class="text-center">
                                        <div class="form-group score-input-form-group"><input class="form-control" type="text"></div>
                                        <div class="form-group score-submit-form-group"><button class="btn btn-primary btn-user" type="button" style="background: var(--indigo);">Submit Score</button></div>
                                    </form>
                                </div>
                                <div class="col">
                                    <form class="text-center">
                                        <div class="form-group score-input-form-group"><input class="form-control" type="text"></div>
                                        <div class="form-group score-submit-form-group"><button class="btn btn-primary btn-user" type="button" style="background: var(--indigo);">Submit Score</button></div>
                                    </form>
                                </div>
                                 -->
                                 
                                 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="bg-white sticky-footer">
                <div class="container my-auto">
                    <div class="text-center my-auto copyright"><span>Copyright Â© CMS 2021</span></div>
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