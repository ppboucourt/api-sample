/**
 * Created by hermeslm on 10/2/16.
 */

(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('HeaderController', HeaderController);

    HeaderController.$inject = ['$scope', '$state', 'Auth', 'Principal', 'ProfileService',
        'LoginService', 'AdminLTEService', 'APP', 'Facility', 'Employee', 'CoreService', 'Idle', '$uibModal', 'IDLE', '$rootScope', '$timeout'];

    function HeaderController ($scope, $state, Auth, Principal, ProfileService, LoginService,
                               AdminLTEService, APP, Facility, Employee, CoreService, Idle, $uibModal, IDLE, $rootScope, $timeout) {
        var vm = this;

        vm.appName = APP.APP_NAME;

        vm.appSuffixName = APP.APP_SUFFIX_NAME;
        vm.appShortName = APP.APP_SHORT_NAME;
        vm.toggleBtn = '[data-toggle="offcanvas"]';
        vm.checkFacility = null;
        vm.account = null;
        vm.employeeAccount = null;

        vm.isNavbarCollapsed = true;

        //Functions
        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.employeeFullName = employeeFullName;
        vm.roleTypeEmployee = roleTypeEmployee;

        getEmployee();

        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        //Loading Corp/Employee/Facility/User data
        vm.facilities = [];
        vm.currentFacility = null;

        vm.$state = $state;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function employeeFullName() {
            if (vm.account)
                return vm.account.firstName + ' ' + vm.account.lastName;
            else
                return 'System';
        }

        function roleTypeEmployee() {
            if(vm.employee){
                return vm.employee.typeEmployee?vm.employee.typeEmployee.name:'Super Admin';
            }
        }

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('login');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        function getEmployee() {
            Employee.employeeAccount().$promise.then(function (employee) {
                    vm.employee = employee;
                    // vm.employee.avatar = CoreService.getAvatar(employee.gender);
                    CoreService.setCurrentEmployee(vm.employee);
                    // $rootScope.$broadcast('employeeGotten');
                }, function (reason) {
                    console.log('Failed getting employee: ' + reason.statusText);
                }
            );
        }

        vm.countdown = IDLE.timeout;

        closeModals();
        function closeModals() {
            if (vm.warning) {
                vm.warning.close();
                vm.warning = null;
            }
        }

        $rootScope.$on('IdleStart', function() {
            closeModals();

            vm.warning = $uibModal.open({
                templateUrl: 'warning-dialog.html',
                windowClass: 'modal-danger'
            });
        });

        $rootScope.$on('IdleEnd', function() {
            closeModals();

            vm.countdown = IDLE.timeout;
        });

        $rootScope.$on('IdleTimeout', function() {
            closeModals();
            logout();
        });

        Idle.watch();
    }
})();

