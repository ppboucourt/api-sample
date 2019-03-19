(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientResultByPatientController', PatientResultByPatientController);

    PatientResultByPatientController.$inject = ['$compile', '$scope', 'toastr', 'PatientResult', 'chart', 'filterRes', 'moment',
        'PatientResultSearch', '$q', 'DTColumnBuilder', 'DTOptionsBuilder', 'GUIUtils', '$filter', 'PatientOrder', 'PatientOrderItem',
        'PatientResultFile', '$uibModal'];

    function PatientResultByPatientController($compile, $scope, toastr, PatientResult, chart, filterRes, moment,
                                              PatientResultSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter,
                                              PatientOrder, PatientOrderItem, PatientResultFile, $uibModal) {

        var vm = this;

        vm.patientResults = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.chart = chart;
        vm.filterRes = filterRes;

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (vm.isFilter) {
                defer.resolve(vm.patientOrdersResult);
                vm.isFilter = false;
            } else if (!vm.searchQuery || vm.searchQuery == '') {
                // PatientOrder.orders({id: vm.chart.id}).$promise
                $q.all([PatientOrderItem.getOrderItemsCollected({id: vm.chart.id}).$promise, PatientResult.byPatient({id: vm.chart.id}).$promise])
                    .then(function (result) {
                        if (result[0] && result[1]) {
                            vm.patientOrders = result[0];
                            vm.patientResults = result[1];
                            vm.patientOrdersResult = _.flatMap(result);
                            // console.log("result", vm.patientOrdersResult);
                            defer.resolve(vm.patientOrdersResult);
                        }
                    });
            } else {
                defer.resolve($filter('filter')(vm.patientOrdersResult, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withOption('aaSorting', [[2, 'asc']]).withDOM('tip')
            .withOption('fnRowCallback',
                function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                    // if (vm.getForReview(aData) > 0) {
                    //     $('td', nRow).addClass('bg-red-light');
                    // }
                    $compile(nRow)($scope);
                });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle(' ').withOption('width', '45px').renderWith(function (data) {
                return (!data.hasOwnProperty('groupNumberId')) ? '<a class="green shortinfo" href="javascript:;" ng-click="vm.childInfo(' + data.id + ', $event)" ' +
                    'title="Click to view more"><i class="glyphicon glyphicon-plus-sign" />' +
                    '</a>' : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Document type').withOption('width', '120px').renderWith(function (data, type, full) {
                return (data.hasOwnProperty('groupNumberId')) ? 'Requisition' : 'Result';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Barcode').renderWith(function (data, type, full) {
                var barcode = (data.hasOwnProperty('accessionNumber')) ? data.accessionNumber : data.groupNumberId;
                return (barcode) ? barcode : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Vendor').renderWith(function (data, type, full) {
                // return (data.order && data.order.chart) ? data.order.chart.facility.name : '';
                return '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Barcode Source').renderWith(function (data, type, full) {
                return '  ';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Collected').renderWith(function (data, type, full) {
                return (data.collectionDate) ? moment(data.collectionDate).format("MM/DD/Y") : ' ';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Completed').renderWith(function (data, type, full) {
                return '  ';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Status').renderWith(function (data, type, full) {
                return data.status ? data.status : "Requisition sent";
            }),
            DTColumnBuilder.newColumn(null).withTitle('For Review').renderWith(function (data, type, full) {
                var forRev = vm.getForReview(data);
                var bgBadge = (forRev > 0) ? 'label-danger' : 'label-success';
                return '<span class="label ' + bgBadge + '">' + forRev + '<span>';
            }),
            // DTColumnBuilder.newColumn(null).withTitle('DOB').withOption('width', '60px').renderWith(function (data, type, full) {
            //     return (data.dob) ? moment(data.dob).format("MM/DD/Y") : '';
            // }),
            // DTColumnBuilder.newColumn(null).withTitle('Order ID').renderWith(function (data, type, full) {
            //     return data.order ? data.order.id : "";
            // }),
            // DTColumnBuilder.newColumn(null).withTitle('Received Date').renderWith(function (data, type, full) {
            //     return moment(data.createdDate).format("M/DD/Y");
            // }),
            // DTColumnBuilder.newColumn('').withTitle('').withOption('width', '155px').notSortable()
            //     .renderWith(abnormalHtml),
            DTColumnBuilder.newColumn('').withTitle('').withOption('width', '135px').notSortable()
                .renderWith(actionsHtml),
        ];

        vm.getForReview = function (data) {
            var forReview = 0;
            if (data.patientResultDets) {
                forReview = _.chain(data.patientResultDets)
                    .filter(function (row) {
                        return ['L', 'LL', 'H', 'HH'].find(function (s) {
                            return s === row.status.toUpperCase();
                        })
                    })
                    .value()
                    .length;
            }
            return forReview;
        }

        vm.childInfo = function (id, event) {
            var scope = $scope.$new(true);
            //Looking for concurrent review by id
            scope.data = _.find($scope.vm.patientOrdersResult, function (row) {
                return row.id == id;
            });
            var link = angular.element(event.currentTarget),
                icon = link.find('.glyphicon'),
                tr = link.parent().parent(),
                table = $scope.vm.dtInstance.DataTable,
                row = table.row(tr);
            if (row.child.isShown()) {
                icon.removeClass('glyphicon-minus-sign').addClass('glyphicon-plus-sign');
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                icon.removeClass('glyphicon-plus-sign').addClass('glyphicon-minus-sign');
                row.child($compile('<div class="clearfix" pr-details></div>')(scope)).show();
                tr.addClass('shown');
            }
        }

        function abnormalHtml(data, type, full, meta) {
            if (full.abnormal) {
                return GUIUtils.getStatusTemplate('ABNORMAL', 'danger');
            } else {
                return GUIUtils.getStatusTemplate('NORMAL', 'success');
            }
        }

        function actionsHtml(data, type, full, meta) {
            var btnPDF = (full.hasOwnProperty('accessionNumber')) ? '<button type="button" class="btn btn-sm btn-default" ng-click="vm.getPDF(' + full.id + ')"><i class="fa fa-file-pdf-o"></i> PDF</button>' : '';
            var btnDetails = (full.hasOwnProperty('groupNumberId')) ? '<button type="button" class="btn btn-sm btn-default" ng-click="vm.getDetails(' + full.id + ')"><i class="fa fa-file-pdf-o"></i> Details </button>' : '';
            return '<div class="btn-group">' + btnPDF + btnDetails + '</div>';
        }

        vm.getDetails = function (id) {
            var item = _.find(vm.patientOrdersResult, function (row) {
                return row.id === id;
            });
            $uibModal.open({
                templateUrl: 'app/entities/patient-result/patient-lab-requisition-dialog.html',
                controller: 'PatientLabRequisitionDetailController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return item;
                    }
                }
            });
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        vm.popoverFilterEnable = popoverFilterEnable;
        vm.showFilter = false;

        function popoverFilterEnable() {
            vm.showFilter = !vm.showFilter;
        }

        vm.isFilter = false;
        vm.filter = filter;

        function filter() {
            vm.isFilter = true;

            var must = [];
            must.push({term: {"order.chartId": vm.chart.id}});
            must.push({term: {"delStatus": false}});

            if (vm.filterRes.status != null && vm.filterRes.status.length > 0) {
                must.push({match: {status: {query: vm.filterRes.status}}});
            }

            if (vm.filterRes.abnormal != null) { // && vm.filterRes.abnormal.length > 0
                must.push({match: {abnormal: {query: (vm.filterRes.abnormal) ? true : false}}});
            }

            if (vm.filterRes.start) {
                must.push({range: {createdDate: {gte: vm.filterRes.start}}});
            }

            if (vm.filterRes.end) {
                var end = new Date(vm.filterRes.end);
                end.setHours(23, 59, 59);
                must.push({range: {createdDate: {lte: end}}});
            }

            PatientResultSearch.query(
                {
                    query: {
                        bool: {
                            must: must
                        }
                    }
                }, function (result) {
                    // console.log("result", result);
                    vm.patientResults = result;
                    vm.dtInstance.reloadData();
                });
        }

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.start = false;
        vm.datePickerOpenStatus.end = false;

        vm.openCalendar = openCalendar;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.clear = clear;

        function clear() {
            vm.filterRes = {
                patientName: null,
                start: null,
                end: null,
                abnormal: null,
                status: null
            };

            vm.isFilter = false;
            vm.dtInstance.reloadData();
        }

        //
        // vm.getPDF = function (id) {
        //     PatientResult.get_pdf({id: id});
        // }

        vm.getPDF = function (id) {
            PatientResultFile.getByPatientResult(id).$promise
                .catch(function (error) {
                    console.log(error);
                    toastr.error(error.statusText, "Error");
                });
        }
    }
})();
