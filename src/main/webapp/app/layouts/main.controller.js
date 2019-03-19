/**
 * Created by hermeslm on 10/2/16.
 */

(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MainController', MainController);

    MainController.$inject = ['$scope', '$state', 'Auth', 'Principal', 'ProfileService',
        'LoginService', 'AdminLTEService'];

    function MainController ($scope, $state, Auth, Principal, ProfileService, LoginService, AdminLTEService) {
        var vm = this;

        vm.toggleBtn = '[data-toggle="offcanvas"]';

        vm.account = null;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;


        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
