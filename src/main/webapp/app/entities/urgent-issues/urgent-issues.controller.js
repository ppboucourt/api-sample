(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('UrgentIssuesController', UrgentIssuesController);

    UrgentIssuesController.$inject = ['$scope', '$state', 'UrgentIssues', 'UrgentIssuesSearch', '$q', 'DTColumnBuilder' ,
        'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile', 'CoreService', '$rootScope', 'chart', '$uibModal'];

    function UrgentIssuesController ($scope, $state, UrgentIssues, UrgentIssuesSearch, $q, DTColumnBuilder,
                                     DTOptionsBuilder, GUIUtils, $filter, $compile, CoreService, $rootScope, chart, $uibModal ) {
        var vm = this;

        //Variables
        vm.urgentIssues = [];
        vm.urgentIssue = {};
        vm.dtInstance = {};
        $rootScope.unknowledgeIssues = vm.unknowledgeIssues = 0;
        $rootScope.aknowledgeIssues = vm.aknowledgeIssues = 0;

        //Functions
        vm.search = search;
        vm.update = update;
        vm.employeeIssues = employeeIssues;


        // loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                UrgentIssues.issuesByChart({id: chart.id}, function(result) {
                    vm.urgentIssues = result;
                    checkEmployeeAknowledge(result);
                    defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.urgentIssues, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('description').withTitle('Description'),
            DTColumnBuilder.newColumn(null).withTitle('Checked').withOption('width', '155px').notSortable()
                .renderWith(issueChecked),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        // function loadAll() {
        //     UrgentIssues.query(function(result) {
        //         vm.urgentIssues = result;
        //         vm.searchQuery = null;
        //     });
        // }

        function actionsHtml(data, type, full, meta){
            return '<a class="btn-sm btn-primary" ng-click="vm.update(' + data.id + ')" ng-show="'+ data.unknow +'" title="Aknowledge Issue">' +
                '<i class="fa fa-check"></i></a>&nbsp;' +
                '<a class="btn-sm btn-primary" ng-click="vm.employeeIssues(' + data.id + ')" title="Who Know?">' +
                '<i class="fa fa-eye"></i></a>&nbsp;';
        }

        function issueChecked(data) {
            return '<span class="label label-success" ng-show="('+ !data.unknow +')">Yes</span>' +
                '<span class="label label-warning" ng-show="'+ data.unknow +'">No</span>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        function update (id) {
            vm.isSaving = true;
            var i = 0;
            for (var i = 0; i < vm.urgentIssues.length && vm.urgentIssues[i].id != id; i++);
            angular.copy(vm.urgentIssues[i], vm.urgentIssue);
            vm.urgentIssue.employees.push(CoreService.getCurrentEmployee());
            UrgentIssues.update(vm.urgentIssue, onUpdateSuccess, onUpdateError);
        }

        function onUpdateSuccess (result) {
            $scope.$emit('bluebookApp:urgentIssuesUpdate', result);
            vm.isSaving = false;
            $state.go('patient', null, { reload: 'patient' });
        }

        function onUpdateError () {
            vm.isSaving = false;
        }

        function checkEmployeeAknowledge(data) {
            for (var i = 0; i < data.length; i++){
                if(!data[i].employees){
                    vm.unknowledgeIssues ++;
                    data[i].unknow = true;
                }
                else{
                    for(var j = 0; j < data[i].employees.length; j++){
                        if(data[i].employees[j].id == CoreService.getCurrentEmployee().id){
                            vm.aknowledgeIssues ++;
                            data[i].unknow = false;
                            break;
                        }
                    }
                    if(j == data[i].employees.length){
                        vm.unknowledgeIssues ++;
                        data[i].unknow = true;
                    }
                }
            }
            $rootScope.aknowledgeIssues = vm.aknowledgeIssues?vm.aknowledgeIssues:0;
            $rootScope.unknowledgeIssues = vm.unknowledgeIssues?vm.unknowledgeIssues:0;
        }

        //Urgen Issues Employees
        var modalEmployeeIssues = null;

        function employeeIssues(id) {
            if (modalEmployeeIssues !== null) return;
            modalEmployeeIssues = $uibModal.open({
                animation: true,
                backdrop: 'static',
                keyboard: false,
                size: 'md',
                templateUrl: 'app/entities/urgent-issues/urgent-issue-employees.html',
                controller: 'UrgentIssuesEmployeeController',
                controllerAs: 'vm',
                resolve: {
                    urgentIssue: ['$stateParams', 'UrgentIssues', function($stateParams, UrgentIssues) {
                        if(id)
                            return UrgentIssues.get({id : id}).$promise;
                    }]
                }
            });
            modalEmployeeIssues.result.then(
                resetModalEmployeeIssues,
                resetModalEmployeeIssues
            );
        }

        var resetModalEmployeeIssues = function () {
            modalEmployeeIssues = null;
        };

    }
})();
