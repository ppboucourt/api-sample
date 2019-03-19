(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope', '$state', '$timeout', 'Auth', 'APP', 'EmployeeSign', 'CoreService', 'User', 'toastr', '$cookies', 'Base64', '$sessionStorage', '$localStorage'];

    function LoginController($rootScope, $state, $timeout, Auth, APP, EmployeeSign, CoreService, User, toastr, $cookies, Base64, $sessionStorage, $localStorage) {
        var vm = this;
        $rootScope.background = 'background-image: url(content/images/background.jpg); ' + 'background-repeat: no repeat; background-size: cover'

        vm.toastr = toastr;
        vm.$cookies = $cookies;
        vm.authenticationError = false;
        vm.cancel = cancel;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.register = register;
        vm.rememberMe = false;
        vm.requestResetPassword = requestResetPassword;
        vm.username = null;

        vm.appName = APP.APP_NAME;
        vm.appSuffixName = APP.APP_SUFFIX_NAME;

        $timeout(function () {
            angular.element('#username').focus();
        });

        function cancel() {
            vm.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            vm.authenticationError = false;
        }

        function login(event) {
            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {

                EmployeeSign.query({'login': vm.username.toLowerCase()},
                    function (data) {

                            CoreService.setCurrentEmployee(data);

                            vm.authenticationError = false;
                            if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                                $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                            }

                            $rootScope.$broadcast('authenticationSuccess');

                            // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                            // since login is succesful, go to stored previousState and clear previousState
                            if (Auth.getPreviousState()) {
                                var previousState = Auth.getPreviousState();
                                Auth.resetPreviousState();
                                $state.go(previousState.name, previousState.params);
                            }


                            var employee = CoreService.getCurrentEmployee();

                            var fromCookie = CoreService.getUUID();

                            CoreService.deleteValidateCode(); //unlogged


                            User.isTwoFactorNeeded({browserUuid: fromCookie}, function (response) {

                                if(response && response.value == '0' ){
                                    CoreService.saveValidateCode(); //logged

                                    if(data.signature){
                                        $state.go('home');
                                    }else{
                                        $state.go('consent');
                                    }
                                }else{
                                  //  $sessionStorage['tmpCode'+employee.id] = Base64.encode(response.code);
                                    $state.go('two-factor');
                                }

                            }, function (error) {
                                console.log(error);
                            });

                       // }

                    }, function () {
                        vm.authenticationError = false;
                        // $uibModalInstance.close();
                        if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                            $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                            $state.go('login');
                        }

                        $rootScope.$broadcast('authenticationSuccess');

                        // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                        // since login is succesful, go to stored previousState and clear previousState
                        if (Auth.getPreviousState()) {
                            var previousState = Auth.getPreviousState();
                            Auth.resetPreviousState();
                            $state.go(previousState.name, previousState.params);
                        }
                        $state.go('home');
                    });


            }).catch(function () {
                vm.authenticationError = true;
            });
        }

        function register() {
            // $uibModalInstance.dismiss('cancel');
            $state.go('register');
        }

        function requestResetPassword() {
            // $uibModalInstance.dismiss('cancel');
            $state.go('requestReset');
        }
    }
})();
