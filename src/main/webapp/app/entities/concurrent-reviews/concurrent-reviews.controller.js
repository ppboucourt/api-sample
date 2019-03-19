(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ConcurrentReviewsController', ConcurrentReviewsController);

    ConcurrentReviewsController.$inject = ['$scope', '$state', 'ConcurrentReviews', 'ConcurrentReviewsSearch', '$q',
        'DTColumnBuilder', 'DTOptionsBuilder', 'GUIUtils', '$filter',
        'ConcurrentReviewsChart', '$compile', '$stateParams', '$uibModal'];

    function ConcurrentReviewsController($scope, $state, ConcurrentReviews, ConcurrentReviewsSearch, $q,
                                         DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter,
                                         ConcurrentReviewsChart, $compile, $stateParams, $uibModal) {
        var vm = this;
        vm.concurrentReviews = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.dateformat = 'MM/DD/YYYY';
        vm.activeTab = 1;

        // vm.chart = chart;

        // loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {

                ConcurrentReviewsChart.query({'id': $stateParams.id}, function (result) {
                    vm.concurrentReviews = result;
                    $scope.$emit('bluebookApp:concurrentReviewCount', {
                        total: result.length
                    });
                    defer.resolve(result);
                });

            } else {
                defer.resolve($filter('filter')(vm.concurrentReviews, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers')
            .withBootstrap()
            .withDOM('tip')
            .withOption('aaSorting', [])
            .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle(' ').withOption('width', '45px').renderWith(function (data) {
                return '<a class="green shortinfo" href="javascript:;" ng-click="vm.childInfo(' + data.id + ', $event)" ' +
                    'title="Click to view more"><i class="glyphicon glyphicon-plus-sign" />' +
                    '</a>'
            }),
            DTColumnBuilder.newColumn('insuranceCompany').withTitle('Insurance Company'),
            DTColumnBuilder.newColumn('authorizationDate').withTitle('Authorization Date').renderWith(renderDate),
            DTColumnBuilder.newColumn('startDate').withTitle('Start Date').renderWith(renderDate),
            DTColumnBuilder.newColumn('endDate').withTitle('End Date').renderWith(renderDate),
            // DTColumnBuilder.newColumn('notes').withTitle('Notes').renderWith(renderNotes),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        vm.childInfo = function (id, event) {
            var scope = $scope.$new(true);
            //Looking for concurrent review by id
            scope.data = _.find($scope.vm.concurrentReviews, function (row) {
                return row.id == id;
            });
            var link = angular.element(event.currentTarget),
                icon = link.find('.glyphicon'),
                tr = link.parent().parent(),
                table = $scope.vm.dtInstance.DataTable,
                row = table.row(tr);
            //
            if (row.child.isShown()) {
                icon.removeClass('glyphicon-minus-sign').addClass('glyphicon-plus-sign');
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                icon.removeClass('glyphicon-plus-sign').addClass('glyphicon-minus-sign');
                row.child($compile('<div class="clearfix" cr-notes></div>')(scope)).show();
                tr.addClass('shown');
            }
        }

        // function loadAll() {
        //     ConcurrentReviews.query(function (result) {// Here we need filter by chart
        //         vm.concurrentReviews = result;
        //         vm.searchQuery = null;
        //     });
        // }

        function actionsHtml(data, type, full, meta) {
            return '<a class="btn-sm btn-primary" ng-click="vm.saveConcurrentReview(' + data.id + ')">' +
                '<i class="fa fa-edit"></i></a> ' +
                ' <a class="btn-sm btn-danger" ng-click="vm.deleteConcurrentReview(' + data.id + ')">' +
                '<i class="fa fa-trash"></i></a> ';
        }

        vm.saveConcurrentReview = function (id) {
            var entity = null;
            //Edit the entity
            if (id) {
                entity = ConcurrentReviews.get({id: id}).$promise;
            } else {
                entity = {
                    authorizationDate: null,
                    numberDates: null,
                    frequency: null,
                    typeLevelCare: null,
                    startDate: null,
                    endDate: null,
                    lastCoverageDate: null,
                    authorizationNumber: null,
                    nextReviewDate: null,
                    insuranceCompany: null,
                    notes: null,
                    status: null,
                    id: null,
                    chartId: $stateParams.id
                };
            }
            $uibModal.open({
                templateUrl: 'app/entities/concurrent-reviews/concurrent-reviews-dialog.html',
                controller: 'ConcurrentReviewsDialogController',
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

        vm.deleteConcurrentReview = function (id) {
            $uibModal.open({
                templateUrl: 'app/entities/concurrent-reviews/concurrent-reviews-delete-dialog.html',
                controller: 'ConcurrentReviewsDeleteController',
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

        function search() {
            vm.dtInstance.reloadData();
        }

        function renderDate(data, type, full, meta) {
            return data ? moment(data).format(vm.dateformat) : '';//$scope.dateFormat
        }

        function renderTypeLevelCare(data, type, full, meta) {
            return data ? data.name : '';//$scope.dateFormat
        }

        function renderNotes(data, type, full, meta) {
            return data ? data : '';//$scope.dateFormat
        }

        vm.backDetailsBasic = function () {
            $state.go('patient', {}, {reload: true});
        }


        //moment(DateUtils.convertDateTimeFromServer(patient[i].dateBirth)).format($scope.dateFormat);

    }
})();
