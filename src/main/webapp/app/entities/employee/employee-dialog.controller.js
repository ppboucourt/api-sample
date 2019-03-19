(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EmployeeDialogController', EmployeeDialogController);

    EmployeeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Employee',
        'UrgentIssues', 'Chart', 'ROLES', 'User', 'Corporation', 'Utils', 'CoreService', 'TypeEmployee', 'EmployeeSearch'];

    function EmployeeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Employee,
                                       UrgentIssues, Chart, ROLES, User, Corporation, Utils, CoreService, TypeEmployee, EmployeeSearch) {
        var vm = this;

        vm.employee = entity;
        vm.clear = clear;
        vm.save = save;
        vm.searchUser = searchUser;
        vm.searchEmail = searchEmail;
        vm.urgentissues = UrgentIssues.query();
        vm.charts = Chart.query();
        vm.corporations = Corporation.query();
        vm.typeEmployee = TypeEmployee.query();
        vm.authorities = [];// Utils.objectToArray(ROLES);
        vm.userExist = false;
        vm.emailExist = false;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();

            var temp = [];

            if(vm.employee.id){
                vm.employee.user.authorities = vm.employee.user.authorities;

                for(var i = 0; i < vm.employee.user.authorities.length; i++){

                    if(vm.employee.user.authorities[i].name.indexOf('ROLE_USER') < 0){
                        temp.push(vm.employee.user.authorities[i]);
                    }
                }

                vm.employee.user.authorities = temp;
                vm.authorities = vm.employee.typeEmployee.authorities;
            }

            console.log(vm.employee);
        });



        vm.typeEmployeeChange = function(){
            vm.authorities = vm.employee.typeEmployee.authorities;

            if(vm.employee.user){
                vm.employee.user.authorities = [];
            }

        };

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.employee.email = vm.employee.user.email;
            if (vm.employee.id !== null) {
                Employee.update(vm.employee, onSaveSuccess, onSaveError);
            } else {

                vm.employee.corporation = CoreService.getCorporation();
                Employee.save(vm.employee, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {

            $scope.$emit('bluebookApp:employeeUpdate', result);
            $uibModalInstance.close(result);

            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function searchUser(query) {
            if (query && query.length > 2) {
                EmployeeSearch.query({
                    query: {
                        bool: {
                            must: [
                                {match: {"user.login": {query: query}}}
                            ]
                        }
                    }
                }, function (result) {
                    vm.userExist = false;
                    if(result.length) {
                        for (var i = 0; i < result.length && result[i].user.login !== query; i++);
                        if (result[i]) {
                            vm.userExist = true;
                        }
                    }
                });

            }
        }

        function searchEmail(query) {
            if (query && query.length > 2) {
                EmployeeSearch.query({
                    query: {
                        bool: {
                            must: [
                                {match: {"user.email": {query: query}}}
                            ]
                        }
                    }
                }, function (result) {
                    vm.emailExist = false;
                    if(result.length) {
                        for (var i = 0; i < result.length && result[i].user.email !== query; i++);
                        if (result[i]) {
                            vm.emailExist = true;
                        }
                    }
                });

            }
        }

        // function filterPatients(query) {
        //     if (query && query.length > 1) {
        //         vm.currentClinic = CoreService.getCurrentClinic();
        //         PatientSearch.query({
        //             query: {
        //                 bool: {
        //                     should: [
        //                         {match: {firstName: {query: query, fuzziness: 1}}},
        //                         {match: {lastName: {query: query, fuzziness: 1}}}
        //                     ],
        //                     must: [
        //                         {term: {"clinic.id": vm.currentClinic.id}},
        //                         {term: {"delStatus": false}}
        //                     ]
        //                 }
        //             }
        //         }, function (result) {
        //             vm.patients = result;
        //         });
        //     }
        // }


    }
})();
