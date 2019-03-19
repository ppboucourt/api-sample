(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartCareTeamController', ChartCareTeamController);

    ChartCareTeamController.$inject = ['$scope', '$q', '$compile', 'ChartCareTeam', 'DTColumnBuilder', 'DTOptionsBuilder',
        '$uibModal', '$stateParams'];

    function ChartCareTeamController($scope, $q, $compile, ChartCareTeam, DTColumnBuilder, DTOptionsBuilder,
                                     $uibModal, $stateParams) {
        var vm = this;
        vm.$stateParams = $stateParams;
        //
        // Chart.get({id:$stateParams.id}, function (result) {
        //     vm.currentChart = result;
        // })

        vm.activeTab = 16;

        vm.form = {};
        vm.editing = false;
        vm.search = search;
        vm.editingCareTeamMember = false;

        vm.careTeams = [];
        vm.careTeamsDtInstance = {};

        // TypeSpeciality.query(function (result) {
        //     vm.typeSpecialities = result;
        // });

        // Employee.query(function (result) {
        //     vm.employees = result;
        // });

        vm.careTeamsDtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();

            ChartCareTeam.getAllVOByChart({id: vm.$stateParams.id}, function (result) {
                vm.careTeams = result;
                $scope.$emit('bluebookApp:careTeamsCount', {
                    total: result.length
                });
                defer.resolve(result);
            });
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.careTeamsDtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Care Member').renderWith(function (data, type, full) {
                return data.employee.firstName + ' ' + data.employee.lastName;
            }),

            DTColumnBuilder.newColumn(null).withTitle('Type/Specialty').renderWith(function (data, type, full) {
                return data.typeSpeciality.name;
            }),

            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(careTeamsActionsHtml)
        ];

        function careTeamsActionsHtml(data, type, full, meta) {
            return '<a class="btn-sm btn-primary" ng-click="vm.saveTeamCare(' + data.id + ')">' +
                '<i class="fa fa-edit"></i></a> ' +
                ' <a class="btn-sm btn-danger" ng-click="vm.deleteTeamCare(' + data.id + ')">' +
                '<i class="fa fa-trash"></i></a> ';
        }

        vm.saveTeamCare = function (id) {
            var entity = null;
            //Edit the entity
            if (id) {
                entity = ChartCareTeam.get({id: id}).$promise;
            } else {
                entity = {
                    chartId: $stateParams.id
                };
            }
            $uibModal.open({
                templateUrl: 'app/entities/chart-care-team/chart-care-team-add-dialog.html',
                controller: 'ChartCareTeamAddController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function () {
                vm.search();
            }, function (error) {
                console.log("error", error);
                //Error handling
            });
        }

        vm.deleteTeamCare = function (id) {
            $uibModal.open({
                templateUrl: 'app/entities/chart-care-team/chart-care-team-delete-dialog.html',
                controller: 'ChartCareTeamDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: {
                        id: id
                    }
                }
            }).result.then(function () {
                vm.search();
            }, function () {
                //Error
            });
        }


        vm.changeEditingCareTeamState = changeEditingCareTeamState;

        function changeEditingCareTeamState() {
            vm.editingCareTeamMember = !vm.editingCareTeamMember;
        }

        function search() {
            vm.careTeamsDtInstance.reloadData();
        }

    }
})();
