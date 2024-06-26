import React from "react";
import ReactDOM from "react-dom/client";
import {
  RouteObject,
  RouterProvider,
  createBrowserRouter,
} from "react-router-dom";
import Layout from "./pages/Layout";
import { AdminRoutes, ProtectedRoutes, UnprotectedRoutes } from "./routes";
import AuthRouteGuard from "./pages/AuthRouteGuard";
import LoginPage from "./pages/login";
import RegisterPage from "./pages/register";
import "./index.css";
import AuthenticationLayout from "./pages/AuthenticationLayout";
import AdminLayout from "./pages/admin/AdminLayout";
import UserContextProvider from "./context/userContext";
import ResetPasswordEmail from "./pages/reset-password/send-email";
import ChangePassword from "./pages/reset-password/change-password";

const router = createBrowserRouter([
  {
    path: "/",
    Component: Layout,
    children: [
      ...UnprotectedRoutes,
      {
        path: "/",
        Component: AuthRouteGuard,
        children: ProtectedRoutes,
      },
    ],
  },
  {
    path: "/",
    Component: AuthenticationLayout,
    children: [
      { path: "/register", Component: RegisterPage },
      { path: "/login", Component: LoginPage },
      { path: "/reset", Component: ResetPasswordEmail},
      {path: "/change-password/:token", Component: ChangePassword},
    ],
  },
  {
    path: "/admin",
    Component: AdminLayout,
    children: AdminRoutes,
  },
] satisfies RouteObject[]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <UserContextProvider>
      <RouterProvider router={router} />
    </UserContextProvider>
  </React.StrictMode>
);
