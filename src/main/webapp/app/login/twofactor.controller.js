(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TwoFactorController', TwoFactorController);

    TwoFactorController.$inject = ['CoreService', '$state', '$rootScope', 'Employee', 'Auth', 'User', 'toastr', '$cookies', 'Base64', '$sessionStorage'];//, 'previousState'

    function TwoFactorController(CoreService, $state, $rootScope, Employee, Auth, User, toastr, $cookies, Base64, $sessionStorage) {
        var vm = this;

        $rootScope.background = 'background-image: url(content/images/background.jpg); ' + 'background-repeat: no repeat; background-size: cover'

        vm.rememberMe = true;
        vm.toastr = toastr;
        vm.$cookies = $cookies;

        vm.sendCode = sendCode;
        vm.logout = logout;
        vm.resend = resend;
        vm.verify = verify;

        //We check if the employee has signed we redirect to the previous state
        vm.employee = CoreService.getCurrentEmployee();


        function sendCode() {
            var fromCookie = CoreService.getUUID(); //logged

            User.sendSmsCode({browserUuid: fromCookie}, function (data) {

            }, function (error) {
                console.log('Error: ' + error)
            });
        }


        function logout() {
            Auth.logout();
            $state.go('login');
        }

        function onSaveSuccess(result) {
            $state.go("home");
        }

        function onSaveError() {
            vm.isError = false;
        }

        function resend() {
            sendCode();
        }

        function verify() {
            var employee = CoreService.getCurrentEmployee();
            var fromCookie = CoreService.getUUID();

            User.verifyCode({code: vm.code, browserUuid: fromCookie, rememberMe: vm.rememberMe}, function (data) {

                if (data.value == true) {


                    CoreService.saveValidateCode();

                    if (vm.employee && !vm.employee.signature) {
                        $state.go('consent');
                    } else {
                        $state.go('home');
                    }
                } else {
                    alert('Code Wrong!');
                }

            });


        }


        vm.keyPress = function (event) {
            if (event.which == 13 && vm.code.length == 4) {
                vm.verify();
            }
        }

    }
})
();
