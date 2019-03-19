(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('WeightController', WeightController);

    WeightController.$inject = ['$scope', '$state', 'Weight', 'GUIUtils', 'WeightSearch', '$stateParams', '$q', 'DTOptionsBuilder',
        'DTColumnBuilder', '$filter', '$compile', '$rootScope', '$uibModal', 'lodash', 'DatePickerRangeOptions'];

    function WeightController($scope, $state, Weight, GUIUtils, WeightSearch, $stateParams, $q, DTOptionsBuilder, DTColumnBuilder,
                              $filter, $compile, $rootScope, $uibModal, _, DatePickerRangeOptions) {
        var vm = this;
        vm.$stateParams = $stateParams;


        vm.weights = [];
        vm.search = search;
        vm.inmutableWeight = [];
        vm.showLastMeasure = true;
        vm.allWeights = false;
        vm.viewWeightHistoryFlag = false;
        vm.dtInstance = {};

        vm.dateformat = 'MM/dd/yyyy HH:mm';
        vm.filterApplied = false;

        vm.filter = {};
        vm.datePickerRange = {startDate: null, endDate: null};
        vm.datePickerRangeOptions = angular.extend({}, DatePickerRangeOptions, {
            eventHandlers: {
                'apply.daterangepicker': function (ev, picker) {
                    // console.log("ev", ev.model);
                    if (ev.model) {
                        vm.filterWeightDate(ev.model.startDate, ev.model.endDate);
                    }
                }
            }
        });

        vm.marginValues = {
            top: 20,
            right: 100,
            bottom: 40,
            left: 80
        }

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            return vm.loadAll();
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });
        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Date').withOption('width', '155px')
                .renderWith(function (data) {
                    var date = $filter('date')(data.createdDate, "MM/dd/yyyy hh:mm a");
                    return "<span class='badge label-info'>" + date + "</span>";
                }),
            DTColumnBuilder.newColumn('weight').withTitle('Weight (Kgs)'),
            DTColumnBuilder.newColumn('height').withTitle('Height (Mts)'),
            DTColumnBuilder.newColumn(null).withTitle('BMI').renderWith(function (data) {
                return (data.bmi) ? vm.formatBmi(data.bmi) : 'N/A';
            }),
        ];

        vm.datePickerOpenStatusStart = {};
        vm.datePickerOpenStatusEnd = {};

        vm.openCalendarStart = openCalendarStart;
        vm.openCalendarEnd = openCalendarEnd;

        vm.datePickerOpenStatusStart.date = false;
        vm.datePickerOpenStatusStart.time = false;

        vm.datePickerOpenStatusEnd.date = false;
        vm.datePickerOpenStatusEnd.time = false;

        function openCalendarStart(date) {
            vm.datePickerOpenStatusStart[date] = true;
        }

        function openCalendarEnd(date) {
            vm.datePickerOpenStatusEnd[date] = true;
        }


        vm.filterWeightDate = function (start, end) {
            vm.weights = vm.inmutableWeight.filter(function (o) {
                // console.log("between", moment(o.date).isBetween(start, end));
                return moment(o.date).isBetween(start, end);
            });

            vm.calculateAllData();
            vm.typeReportChange();
        }


        vm.cleanFilterWeightDate = function () {
            vm.datePickerRange.startDate = null;
            vm.datePickerRange.endDate = null;
            $('#dateRangeWeight').val(''); //Dont sync

            vm.weights = vm.inmutableWeight.slice();

            vm.calculateAllData();
            vm.setReportDefaults();
        }


        vm.typeReports = [{id: 1, name: "Weight"}, {id: 2, name: "Height"}, {id: 3, name: "Bmi"}];

        vm.typeReport = vm.typeReports[0];
        vm.marginValues = {
            top: 20,
            right: 100,
            bottom: 40,
            left: 80
        };


        $scope.$on('$destroy', $rootScope.$on('bluebookApp:vitalsHistory', function (event, result) {
            var btnTarget = angular.element('div[data-role="weight"]').find('button[data-widget="collapse"]')
            if (result.emitter !== 'weight') {
                vm.viewWeightHistoryFlag = false;
                vm.allWeights = false;
                vm.showLastMeasure = true;
                //Check if the section showed is last measure (default)
                if (result.showLastMeasure) {
                    //Check if the box is minimized to maximized
                    if (btnTarget.find('i.fa-plus').length > 0) {
                        btnTarget.click();
                    }
                } else {
                    if (btnTarget.find('i.fa-minus').length > 0) {
                        btnTarget.click();
                    }
                }
            } else {
                //Check if the box is minimized to maximized
                if (btnTarget.find('i.fa-plus').length > 0) {
                    btnTarget.click();
                }
            }
        }));

        vm.viewAllWeights = function () {
            if (vm.searchQuery) {
                vm.searchQuery = '';
                vm.search();
            }
            vm.allWeights = !vm.allWeights;
            //Hide graph
            vm.viewWeightHistoryFlag = false;
            //Show default measures if the grid and the history are hidden
            vm.showLastMeasure = (!vm.allWeights && !vm.viewWeightHistoryFlag) ? true : false;
            $scope.$emit('bluebookApp:vitalsHistory', {
                emitter: 'weight',
                showLastMeasure: vm.showLastMeasure
            });
        }

        vm.viewWeigthHistory = function () {
            vm.viewWeightHistoryFlag = !vm.viewWeightHistoryFlag;
            //Hide grid
            vm.allWeights = false;
            //Show default measures if the grid and the history are hidden
            vm.showLastMeasure = (!vm.allWeights && !vm.viewWeightHistoryFlag) ? true : false;
            $scope.$emit('bluebookApp:vitalsHistory', {
                emitter: 'weight',
                showLastMeasure: vm.showLastMeasure
            });
        }

        vm.addWeightMeasurement = function () {
            $uibModal.open({
                templateUrl: 'app/entities/weight/weight-dialog.html',
                controller: 'WeightDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            weight: null,
                            height: null,
                            bmi: null,
                            status: null,
                            id: null,
                            date: null,
                            chartId: vm.$stateParams.id
                        };
                    }
                }
            }).result.then(function () {
                vm.search();
            }, function () {
                //Error
            });
        }

        vm.filterArray = function (data) {
            return !data.hidden;
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        vm.optionsWeight = {
            refreshDataOnly: true,
            chart: {
                type: 'lineChart',
                height: 300,
                margin: vm.marginValues,
                x: function (d) {
                    return d.x;
                },
                y: function (d) {
                    return d.y;
                },
                useInteractiveGuideline: true,
                dispatch: {
                    stateChange: function (e) {
                        console.log("stateChange");
                    },
                    changeState: function (e) {
                        console.log("changeState");
                    },
                    tooltipShow: function (e) {
                        console.log("tooltipShow");
                    },
                    tooltipHide: function (e) {
                        console.log("tooltipHide");
                    }
                },
                xAxis: {
                    axisLabel: 'Time (days)',
                    tickFormat: (function (d) {
                        return d3.time.format('%c')(new Date(d));
                    })
                },
                yAxis: {
                    axisLabel: 'Kg (s)',
                },
                callback: function (chart) {
                    console.log("!!! lineChart callback !!!");
                }
            },
            title: {
                enable: true,
                html: '<b>History Weight</b>'
            },
            subtitle: {
                enable: true,
                text: 'History Weight(Kg) in the last period of time ',
                css: {
                    'text-align': 'center',
                    'margin': '10px 13px 0px 7px'
                }
            },
            caption: {
                enable: true,
                html: '<b>Figure 1.</b> Legend',
                css: {
                    'text-align': 'justify',
                    'margin': '10px 13px 0px 7px'
                }
            }
        };

        vm.optionsHeight = {
            refreshDataOnly: true,
            chart: {
                type: 'lineChart',
                height: 300,
                margin: vm.marginValues,
                x: function (d) {
                    return d.x;
                },
                y: function (d) {
                    return d.y;
                },
                useInteractiveGuideline: true,
                dispatch: {
                    stateChange: function (e) {
                        console.log("stateChange");
                    },
                    changeState: function (e) {
                        console.log("changeState");
                    },
                    tooltipShow: function (e) {
                        console.log("tooltipShow");
                    },
                    tooltipHide: function (e) {
                        console.log("tooltipHide");
                    }
                },
                xAxis: {
                    axisLabel: 'Time (days)',
                    tickFormat: (function (d) {
                        return d3.time.format('%c')(new Date(d));
                    })
                },
                yAxis: {
                    axisLabel: 'Mt (s)',
                },
                callback: function (chart) {
                    console.log("!!! lineChart callback !!!");
                }
            },
            title: {
                enable: true,
                html: '<b>History Height</b>'
            },
            subtitle: {
                enable: true,
                text: 'History Height(Mts) in the last time period',
                css: {
                    'text-align': 'center',
                    'margin': '10px 13px 0px 7px'
                }
            },
            caption: {
                enable: true,
                html: '<b>Figure 1.</b> Legend',
                css: {
                    'text-align': 'justify',
                    'margin': '10px 13px 0px 7px'
                }
            }
        };

        vm.optionsBmi = {
            refreshDataOnly: true,
            chart: {
                type: 'lineChart',
                height: 300,
                margin: vm.marginValues,
                x: function (d) {
                    return d.x;
                },
                y: function (d) {
                    return d.y;
                },
                useInteractiveGuideline: true,
                dispatch: {
                    stateChange: function (e) {
                        console.log("stateChange");
                    },
                    changeState: function (e) {
                        console.log("changeState");
                    },
                    tooltipShow: function (e) {
                        console.log("tooltipShow");
                    },
                    tooltipHide: function (e) {
                        console.log("tooltipHide");
                    }
                },
                xAxis: {
                    axisLabel: 'Time (days)',
                    tickFormat: (function (d) {
                        return d3.time.format('%c')(new Date(d));
                    })
                },
                yAxis: {
                    axisLabel: 'Point (s)',
                },
                callback: function (chart) {
                    console.log("!!! lineChart callback !!!");
                }
            },
            title: {
                enable: true,
                html: '<b>History Bmi</b>'
            },
            subtitle: {
                enable: true,
                text: 'History Bmi(Points) in the last time period',
                css: {
                    'text-align': 'center',
                    'margin': '10px 13px 0px 7px'
                }
            },
            caption: {
                enable: true,
                html: '<b>Figure 1.</b> Legend',
                css: {
                    'text-align': 'justify',
                    'margin': '10px 13px 0px 7px'
                }
            }
        };

        vm.loadAll = function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Weight.byChart({id: vm.$stateParams.id}, function (result) {
                    vm.weights = _.sortBy(result, ['createdDate'], ['desc']);
                    defer.resolve(vm.weights);
                    vm.inmutableWeight = vm.weights.slice();

                    if (vm.weights && vm.weights.length == 0) {
                        vm.weights.push({weight: 'N/A', height: 'N/A', bmi: 'N/A', hidden: true});
                    } else {
                        vm.calculateAllData();
                    }

                    vm.setReportDefaults();

                });
            } else {
                defer.resolve($filter('filter')(vm.weights, vm.searchQuery, undefined));
            }

            return defer.promise;
        }

        /*Data Generator */
        vm.calculateAllData = function () {

            vm.dataWeight = [];
            vm.dataHeight = [];
            vm.dataBmi = [];

            vm.weights = vm.weights.sort(function (a, b) {
                // Turn your strings into dates, and then subtract them
                // to get a value that is either negative, positive, or zero.
                return new Date(b.date) - new Date(a.date);
            });

            for (var j = 0; j < vm.weights.length; j++) {

                var newDate = new Date(vm.weights[j].date);

                vm.dataWeight.push({x: newDate, y: parseInt(vm.weights[j].weight)});
                vm.dataHeight.push({x: newDate, y: parseInt(vm.weights[j].height)});
                vm.dataBmi.push({x: newDate, y: parseFloat(vm.weights[j].bmi).toFixed(2)});

            }

        };

        vm.formatBmi = function (num) {
            return parseFloat(num).toFixed(2);
        }

        vm.setReportDefaults = function () {
            vm.dataTemp = [
                {
                    values: vm.dataWeight,      //values - represents the array of {x,y} data points
                    key: 'Weight', //key  - the name of the series.
                    color: '#ff7f0e'  //color - optional: choose your own line color.
                }]; // By default

            vm.data = vm.dataTemp;
            vm.options = vm.optionsWeight;

        }

        vm.typeReportChange = function () {

            switch (vm.typeReport.id) {
                case 1: {
                    vm.dataTemp = [
                        {
                            values: vm.dataWeight,      //values - represents the array of {x,y} data points
                            key: 'Weight', //key  - the name of the series.
                            color: '#6129ff'  //color - optional: choose your own line color.
                        }]; // By default


                    vm.data = vm.dataTemp;
                    vm.options = vm.optionsWeight;

                    break;

                }
                case 2: {
                    vm.dataTemp = [
                        {
                            values: vm.dataHeight,      //values - represents the array of {x,y} data points
                            key: 'Height', //key  - the name of the series.
                            color: '#32ff47'  //color - optional: choose your own line color.
                        }];

                    vm.data = vm.dataTemp;
                    vm.options = vm.optionsHeight;
                    break;

                }
                case 3: {
                    vm.dataTemp = [
                        {
                            values: vm.dataBmi,      //values - represents the array of {x,y} data points
                            key: 'Bmi', //key  - the name of the series.
                            color: '#ff204c'  //color - optional: choose your own line color.
                        }];

                    vm.data = vm.dataTemp;
                    vm.options = vm.optionsBmi;
                    break;
                }
            }
        }

        // vm.loadAll();

    }
})();
